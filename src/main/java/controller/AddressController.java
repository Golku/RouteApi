package controller;

import model.*;
import model.pojos.*;
import java.util.List;

public class AddressController extends BaseController{

    private ContainerManager containerManager;
    private DbManager dbManager;
    private GoogleMapsApi googleMapsApi;
    private AddressFormatter addressFormatter;

    public AddressController() {
        containerManager = getContainerManager();
        dbManager = getDbManager();
        googleMapsApi = getGoogleMapsApi();
        addressFormatter = getAddressFormatter();
    }

    private void validateAddress(Address address){
        googleMapsApi.verifyAddress(address);

        if(address.isValid()){
            addressFormatter.format(address);
        }

        if(address.isValid()){
            dbManager.getAddressInfo(address);
        }
    }

    public Address getAddress(AddressRequest request){
        Address address = new Address();
        address.setAddress(request.getAddress());

        validateAddress(address);

        if(address.isValid()) {
            containerManager.putAddressInList(request.getUsername(), address);
        }

        return address;
    }

    public void updatePackageCount(UpdatePackageCountRequest request){

        Container container = containerManager.getContainer(request.getUsername());

        if(container.getAddressList().size() > 0){
            for (Address address : container.getAddressList()) {
                for(String addressString : request.getAddressList()){
                    if(address.getAddress().equals(addressString)){
                        if (address.isValid()) {
                            int count = request.getCountList().get(request.getAddressList().indexOf(addressString));
                            address.setPackageCount(count);
                        }
                        break;
                    }
                }
            }
        }
    }

    public void removeAddress(RemoveAddressRequest request){
//        System.out.println("Removing: "+request.getAddress() +" for user: " + request.getUsername());
        List<Address> addressList = containerManager.getContainer(request.getUsername()).getAddressList();

        for(Address it: addressList){
            if(it.getAddress().equals(request.getAddress())){
                addressList.remove(it);
                break;
            }
        }
    }
}

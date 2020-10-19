package controller;

import model.*;
import model.pojos.*;
import java.util.List;

public class AddressController extends BaseController{

    private final ContainerManager containerManager;
    private final DbManager dbManager;
    private final GoogleMapsApi googleMapsApi;
    private final GraphhopperApi graphhopperApi;
    private final AddressFormatter addressFormatter;

    public AddressController() {
        containerManager = getContainerManager();
        dbManager = getDbManager();
        googleMapsApi = getGoogleMapsApi();
        graphhopperApi = getGraphhopperApi();
        addressFormatter = getAddressFormatter();
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

    private void validateAddress(Address address){

        graphhopperApi.geocodeAddress(address);

        if(!address.isValid()){
            googleMapsApi.geocodeAddress(address);
        }

        if(address.isValid()){
            addressFormatter.format(address);
        }

        if(address.isValid()){
            googleMapsApi.searchForBusinessNearAddress(address);
            googleMapsApi.searchForBusinessNearLocation(address);
            if(address.getBusinessName() != null && address.getBusinessName().size() > 0){
                address.setChosenBusinessName(address.getBusinessName().get(0));
            }
        }

//        if(address.getBusinessName() != null){
//            googleMapsApi.getAddressDetails(address);
//        }

        if(address.isValid()){
            dbManager.getAddressInfo(address);
        }
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

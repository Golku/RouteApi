package controller;

import model.*;
import model.pojos.*;

import java.util.ArrayList;
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
        address.setUserInputted(true);

        Container container = containerManager.getContainer(request.getUsername());

        if(container.getAddressList() == null){
            container.setAddressList(new ArrayList<Address>());
        }

        List<Address> addressList = container.getAddressList();

        validateAddress(address);

        containerManager.putAddressInList(addressList, address);

        return address;
    }

    public Address changeAddress(AddressChangeRequest request){
        Address address = new Address();
        address.setAddress(request.getNewAddress());

        List<Address> addressList = containerManager.getContainer(request.getUsername()).getAddressList();

        for(Address it: addressList){
            if(it.getAddress().equals(request.getOldAddress())){
                validateAddress(address);
                addressList.remove(it);
                containerManager.putAddressInList(addressList, address);
                break;
            }
        }

        return address;
    }

    public void removeAddress(RemoveAddressRequest request){
        System.out.println("Removing: "+request.getAddress() +" for user: " + request.getUsername());
        List<Address> addressList = containerManager.getContainer(request.getUsername()).getAddressList();

        for(Address it: addressList){
            System.out.println("Checking: " + it.getAddress());
            //case sensitive fix this!
            if(it.getAddress().equals(request.getAddress())){
                System.out.println("Found it");
                addressList.remove(it);
                break;
            }
        }
    }
}

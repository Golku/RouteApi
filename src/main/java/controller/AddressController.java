package controller;

import model.AddressFormatter;
import model.ContainerManager;
import model.DbManager;
import model.GoogleMapsApi;
import model.pojos.Address;
import model.pojos.AddressRequest;
import model.pojos.Container;
import java.util.ArrayList;

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

    public Address getAddress(AddressRequest request){
        Address address = new Address();

        address.setAddress(request.getAddress());

        googleMapsApi.verifyAddress(address);

        if(address.isValid()){
            addressFormatter.format(address);
        }

        if(address.isValid()){
            dbManager.getAddressInfo(address);
        }

        Container container = containerManager.getContainer(request.getUsername());

        if(container.getUserAddressList() == null){
            container.setUserAddressList(new ArrayList<Address>());
        }

        System.out.println("address added to list");
        container.getUserAddressList().add(address);

        return address;
    }
}

package controller;

import model.AddressFormatter;
import model.ContainerManager;
import model.DbManager;
import model.GoogleMapsApi;
import model.pojos.Address;
import model.pojos.AddressRequest;
import model.pojos.Container;
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

    public Address getAddress(AddressRequest request){
        Address address = new Address();
        address.setAddress(request.getAddress());
        address.setUserInputted(true);

        Container container = containerManager.getContainer(request.getUsername());

        if(container.getAddressList() == null){
            container.setAddressList(new ArrayList<Address>());
        }

        List<Address> addressList = container.getAddressList();

        googleMapsApi.verifyAddress(address);

        if(address.isValid()){
            addressFormatter.format(address);
        }

        if(address.isValid()){
            dbManager.getAddressInfo(address);
        }

        //check if address already exist in list, if so add one to packageCount.
        //But if address does not exist in list add the address to the list.
        containerManager.putAddressInList(addressList, address);

        return address;
    }
}

package model;

import model.pojos.Address;
import model.pojos.Container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContainerManager {

    //Change this to a vectors to make it usable with more threads
    private static Map<String, Container> containers = new HashMap<>();

    private Container createContainer(String username){
        Container container = new Container();
        container.setUsername(username);
        containers.put(username, container);
        return container;
    }

    public Container getContainer(String username){
        Container container = containers.get(username);
        if(container == null){
            container = createContainer(username);
        }
        setAddressTypeCount(container);
        return container;
    }

    private void setAddressTypeCount(Container container) {

        int privateAddressCount = 0;
        int businessAddressCount = 0;
        int invalidAddressCount = 0;

        List<Address> addressList = new ArrayList<>();

        if(container.getUserAddressList() != null){
            addressList.addAll(container.getUserAddressList());
        }
        if(container.getRouteAddressList() != null){
            addressList.addAll(container.getRouteAddressList());
        }

        for (Address address : addressList) {
            if (address.isValid()) {
                if (address.isBusiness()) {
                    businessAddressCount++;
                } else {
                    privateAddressCount++;
                }
            } else {
                invalidAddressCount++;
            }
        }

        container.setPrivateAddressCount(privateAddressCount);
        container.setBusinessAddressCount(businessAddressCount);
        container.setInvalidAddressCount(invalidAddressCount);
    }
}
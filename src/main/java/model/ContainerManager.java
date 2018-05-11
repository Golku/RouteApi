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

    public void putAddressInList(List<Address> addressList, Address address){
        boolean notFound = true;
        for(Address it : addressList){
            if(it.getAddress().equals(address.getAddress())){
                it.setPackageCount(it.getPackageCount()+1);
                notFound = false;
            }
        }
        if(notFound){
            addressList.add(address);
        }
    }

    private void setAddressTypeCount(Container container) {

        if(container.getAddressList() == null){
            return;
        }

        int privateAddressCount = 0;
        int businessAddressCount = 0;
        int invalidAddressCount = 0;

        List<Address> addressList = container.getAddressList();

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
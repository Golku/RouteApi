package model;

import model.pojos.Address;
import model.pojos.Container;
import model.pojos.Drive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContainerManager {

    //Change this to a vectors to make it usable with more threads
    private static final Map<String, Container> containers = new HashMap<>();

    private Container createContainer(String username){
        Container container = new Container();
        container.setUsername(username);
        container.setAddressList(new ArrayList<Address>());
        container.setDriveList(new ArrayList<Drive>());
        containers.put(username, container);
        return container;
    }

    public Container getContainer(String username){
        Container container = containers.get(username);
        if(container == null){
            container = createContainer(username);
        }
        return container;
    }

    public void putAddressInList(String username, Address address){
        boolean notFound = true;
        Container container = getContainer(username);
        List<Address> addressList = container.getAddressList();
        for(Address it : addressList){
            if(it.getAddress().equals(address.getAddress())){
                it.setPackageCount(it.getPackageCount()+1);
                address.setPackageCount(it.getPackageCount());
                notFound = false;
            }
        }
        if(notFound){
            address.setPackageCount(1);
            addressList.add(address);
            setAddressTypeCount(container);
        }
    }

    public void setAddressTypeCount(Container container) {

        if(container.getAddressList().isEmpty()){
            return;
        }

        int privateAddressCount = 0;
        int businessAddressCount = 0;

        List<Address> addressList = container.getAddressList();

        for (Address address : addressList) {
            if (address.isValid()) {
                if (address.isBusiness()) {
                    businessAddressCount++;
                } else {
                    privateAddressCount++;
                }
            }
        }

        container.setPrivateAddressCount(privateAddressCount);
        container.setBusinessAddressCount(businessAddressCount);
    }
}
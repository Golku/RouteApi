package controller;

import model.ContainerManager;
import model.DbManager;
import model.pojos.Address;
import model.pojos.Container;

public class ContainerController extends BaseController{

    private DbManager dbManager;
    private ContainerManager containerManager;

    public ContainerController() {
        dbManager = getDbManager();
        containerManager = getContainerManager();
    }

    public Container fetchContainer(String username) {
        Container container = containerManager.getContainer(username);

        if(container.getAddressList() != null){

            for(Address address: container.getAddressList()){

                if(address.isValid()) {
                    dbManager.getAddressInfo(address);
                }
            }
        }

        return container;
    }
}

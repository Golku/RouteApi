package controller;

import model.ContainerManager;
import model.DbManager;
import model.pojos.Address;
import model.pojos.Container;
import model.pojos.UpdatePackageCountRequest;

public class ContainerController extends BaseController{

    private DbManager dbManager;
    private ContainerManager containerManager;

    public ContainerController() {
        dbManager = getDbManager();
        containerManager = getContainerManager();
    }

    public Container fetchContainer(String username) {

        Container container = containerManager.getContainer(username);

        if(container.getAddressList().size() > 0){

            for (Address address : container.getAddressList()) {
                if (address.isValid()) {
                    dbManager.getAddressInfo(address);
                }
            }

            containerManager.setAddressTypeCount(container);
        }

        return container;
    }
}

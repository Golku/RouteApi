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
        return containerManager.getContainer(username);
    }
}

package controller;

import model.ContainerManager;
import model.pojos.Container;
import model.pojos.Route;

public class ContainerController extends BaseController{

    private ContainerManager containerManager;

    public ContainerController() {
        containerManager = getContainerManager();
    }

    public Container fetchContainer(String username) {
        return containerManager.getContainer(username);
    }
}

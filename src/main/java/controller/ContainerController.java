package controller;

import model.ContainerManager;
import model.pojos.Container;

public class ContainerController {

    private ContainerManager containerManager;

    public ContainerController() {
        this.containerManager = new ContainerManager();
    }

    public Container fetchContainer(String username) {
        Container container = containerManager.getContainer(username);

        if (container == null){
            container = containerManager.createContainer(username);
        }

        return container;
    }
}

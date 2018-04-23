package model;

import model.pojos.Container;

import java.util.HashMap;
import java.util.Map;

public class ContainerManager {

    //Change this to a vectors to make it usable with more threads
    private static Map<String, Container> containers = new HashMap<>();

    public Container createContainer(String username){
        Container container = new Container();
        container.setUsername(username);
        containers.put(username, container);
        return container;
    }

    public Container getContainer(String username){
        return containers.get(username);
    }


}
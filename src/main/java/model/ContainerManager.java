package model;

import model.pojos.Container;
import model.pojos.Route;

import java.util.HashMap;
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
        return container;
    }

    public void addRoute(String username, Route route){
        Container container = getContainer(username);
        container.setRoute(route);
    }
}
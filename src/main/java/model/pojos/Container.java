package model.pojos;

import java.util.List;

public class Container {

    private String username;
    private Route route;
    private List<String> userAddresses;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<String> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<String> userAddresses) {
        this.userAddresses = userAddresses;
    }

}

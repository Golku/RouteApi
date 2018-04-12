package model.pojos;

import java.util.List;

public class RouteResponse {

    private int routeState;
    private Route route;
    private List<String> invalidAddresses;

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<String> getInvalidAddresses() {
        return invalidAddresses;
    }

    public void setInvalidAddresses(List<String> invalidAddresses) {
        this.invalidAddresses = invalidAddresses;
    }
}



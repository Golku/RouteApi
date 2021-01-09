package model.pojos.openrouteservice;

import java.util.List;

public class RouteOptimizationResponse {

    public List<Unassigned> unassigned;
    public List<Route> routes;

    public List<Unassigned> getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(List<Unassigned> unassigned) {
        this.unassigned = unassigned;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}

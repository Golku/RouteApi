package model.pojos;

import java.util.List;

public class OptimizedRoute {

    Address endLocation;
    List<Drive> organizedRoute;

    public Address getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Address endLocation) {
        this.endLocation = endLocation;
    }

    public List<Drive> getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(List<Drive> organizedRoute) {
        this.organizedRoute = organizedRoute;
    }
}

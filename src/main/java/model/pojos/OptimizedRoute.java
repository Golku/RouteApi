package model.pojos;

import java.util.List;

public class OptimizedRoute {

    Address originAddress;
    List<Drive> organizedRoute;

    public Address getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(Address originAddress) {
        this.originAddress = originAddress;
    }

    public List<Drive> getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(List<Drive> organizedRoute) {
        this.organizedRoute = organizedRoute;
    }
}

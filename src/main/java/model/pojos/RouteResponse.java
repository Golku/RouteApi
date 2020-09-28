package model.pojos;

import java.util.List;

public class RouteResponse {

    List<Drive> organizedRoute;

    public List<Drive> getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(List<Drive> organizedRoute) {
        this.organizedRoute = organizedRoute;
    }
}

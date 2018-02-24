package model.pojos;

public class RouteApiResponse {

    private boolean routeReceived;
    private boolean organizingRoute;
    private OrganizedRoute organizedRoute;

    public boolean isRouteReceived() {
        return routeReceived;
    }

    public void setRouteReceived(boolean routeReceived) {
        this.routeReceived = routeReceived;
    }

    public boolean isOrganizingRoute() {
        return organizingRoute;
    }

    public void setOrganizingRoute(boolean organizingRoute) {
        this.organizingRoute = organizingRoute;
    }

    public OrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }
}

package model.pojos;

public class Response {

    private boolean organizingInProgress;
    private boolean routeHasInvalidAddresses;
    private OrganizedRoute organizedRoute;

    public boolean isOrganizingInProgress() {
        return organizingInProgress;
    }

    public void setOrganizingInProgress(boolean organizingInProgress) {
        this.organizingInProgress = organizingInProgress;
    }

    public boolean routeHasInvalidAddresses() {
        return routeHasInvalidAddresses;
    }

    public void setRouteHasInvalidAddresses(boolean routeHasInvalidAddresses) {
        this.routeHasInvalidAddresses = routeHasInvalidAddresses;
    }

    public OrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }
}

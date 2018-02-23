package model.pojos;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private boolean organizingInProgress;
    private boolean routeHasInvalidAddresses;
    private boolean routeIsNull;
    private OrganizedRoute organizedRoute;
    private ArrayList<String> invalidAddresses;

    public boolean isOrganizingInProgress() {
        return organizingInProgress;
    }

    public void setOrganizingInProgress(boolean organizingInProgress) {
        this.organizingInProgress = organizingInProgress;
    }

    public boolean isRouteHasInvalidAddresses() {
        return routeHasInvalidAddresses;
    }

    public void setRouteHasInvalidAddresses(boolean routeHasInvalidAddresses) {
        this.routeHasInvalidAddresses = routeHasInvalidAddresses;
    }

    public boolean isRouteIsNull() {
        return routeIsNull;
    }

    public void setRouteIsNull(boolean routeIsNull) {
        this.routeIsNull = routeIsNull;
    }

    public OrganizedRoute getOrganizedRoute() {
        return organizedRoute;
    }

    public void setOrganizedRoute(OrganizedRoute organizedRoute) {
        this.organizedRoute = organizedRoute;
    }

    public ArrayList<String> getInvalidAddresses() {
        return invalidAddresses;
    }

    public void setInvalidAddresses(ArrayList<String> invalidAddresses) {
        this.invalidAddresses = invalidAddresses;
    }
}


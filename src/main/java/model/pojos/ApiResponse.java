package model.pojos;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {

    private String requestType;
    private boolean organizingInProgress;
    private boolean addressValidatingInProgress;
    private boolean routeHasInvalidAddresses;
    private boolean routeIsNull;
    private OrganizedRoute organizedRoute;
    private ArrayList<String> invalidAddresses;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public boolean getOrganizingInProgress() {
        return organizingInProgress;
    }

    public void setOrganizingInProgress(boolean organizingInProgress) {
        this.organizingInProgress = organizingInProgress;
    }

    public boolean getAddressValidatingInProgress() {
        return addressValidatingInProgress;
    }

    public void setAddressValidatingInProgress(boolean addressValidatingInProgress) {
        this.addressValidatingInProgress = addressValidatingInProgress;
    }

    public boolean getRouteHasInvalidAddresses() {
        return routeHasInvalidAddresses;
    }

    public void setRouteHasInvalidAddresses(boolean routeHasInvalidAddresses) {
        this.routeHasInvalidAddresses = routeHasInvalidAddresses;
    }

    public boolean getRouteIsNull() {
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


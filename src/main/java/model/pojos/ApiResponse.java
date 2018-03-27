package model.pojos;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {

    private String requestType;
    private int routeState;
    private SingleDrive singleDrive;
    private ArrayList<FormattedAddress> validAddresses;
    private OrganizedRoute organizedRoute;
    private ArrayList<String> invalidAddresses;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public SingleDrive getSingleDrive() {
        return singleDrive;
    }

    public void setSingleDrive(SingleDrive singleDrive) {
        this.singleDrive = singleDrive;
    }

    public ArrayList<FormattedAddress> getValidAddresses() {
        return validAddresses;
    }

    public void setValidAddresses(ArrayList<FormattedAddress> validAddresses) {
        this.validAddresses = validAddresses;
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



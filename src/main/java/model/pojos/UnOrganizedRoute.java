package model.pojos;

import java.util.List;

public class UnOrganizedRoute {

    private String routeCode;
    private String origin;
    private int routeState;

    private List<FormattedAddress> allValidatedAddressesList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<FormattedAddress> wrongAddressesList;

    public UnOrganizedRoute(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public List<FormattedAddress> getAllValidatedAddressesList() {
        return allValidatedAddressesList;
    }

    public void setAllValidatedAddressesList(List<FormattedAddress> allValidatedAddressesList) {
        this.allValidatedAddressesList = allValidatedAddressesList;
    }

    public List<FormattedAddress> getPrivateAddressList() {
        return privateAddressList;
    }

    public void setPrivateAddressList(List<FormattedAddress> privateAddressList) {
        this.privateAddressList = privateAddressList;
    }

    public List<FormattedAddress> getBusinessAddressList() {
        return businessAddressList;
    }

    public void setBusinessAddressList(List<FormattedAddress> businessAddressList) {
        this.businessAddressList = businessAddressList;
    }

    public List<FormattedAddress> getWrongAddressesList() {
        return wrongAddressesList;
    }

    public void setWrongAddressesList(List<FormattedAddress> wrongAddressesList) {
        this.wrongAddressesList = wrongAddressesList;
    }
}

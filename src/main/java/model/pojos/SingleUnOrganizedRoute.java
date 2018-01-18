package model.pojos;

import java.util.List;

public class SingleUnOrganizedRoute {

    private String routeCode;

    private List<FormattedAddress> allValidatedAddressesList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<FormattedAddress> wrongAddressesList;

    public SingleUnOrganizedRoute(String routeCode, List<FormattedAddress> allValidatedAddressesList, List<FormattedAddress> privateAddressList, List<FormattedAddress> businessAddressList, List<FormattedAddress> wrongAddressesList) {
        this.routeCode = routeCode;
        this.allValidatedAddressesList = allValidatedAddressesList;
        this.privateAddressList = privateAddressList;
        this.businessAddressList = businessAddressList;
        this.wrongAddressesList = wrongAddressesList;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
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

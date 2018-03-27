package model.pojos;

import java.util.ArrayList;
import java.util.List;

public class UnOrganizedRoute {

    private String routeCode;
    private String origin;
    private int routeState;
    private ArrayList<String> addressList;

    private ArrayList<FormattedAddress> validAddressesList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<FormattedAddress> invalidAddressesList;

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

    public ArrayList<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<String> addressList) {
        this.addressList = addressList;
    }

    public ArrayList<FormattedAddress> getValidAddressesList() {
        return validAddressesList;
    }

    public void setValidAddressesList(ArrayList<FormattedAddress> validAddressesList) {
        this.validAddressesList = validAddressesList;
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

    public List<FormattedAddress> getInvalidAddressesList() {
        return invalidAddressesList;
    }

    public void setInvalidAddressesList(List<FormattedAddress> invalidAddressesList) {
        this.invalidAddressesList = invalidAddressesList;
    }
}

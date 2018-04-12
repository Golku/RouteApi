package model.pojos;

import java.util.List;

public class UnorganizedRoute {

    private String routeCode;
    private String origin;
    private int routeState;

    private List<FormattedAddress> addressList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<FormattedAddress> invalidAddressesList;

    public UnorganizedRoute(String routeCode) {
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

    public List<FormattedAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<FormattedAddress> addressList) {
        this.addressList = addressList;
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

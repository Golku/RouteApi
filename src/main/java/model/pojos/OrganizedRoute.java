package model.pojos;

import java.util.List;

public class OrganizedRoute {

    private String routeCode;
    private List<FormattedAddress> addressList;
    private List<FormattedAddress> privateAddressList;
    private List<FormattedAddress> businessAddressList;
    private List<SingleDrive> routeList;

    public OrganizedRoute(String routeCode,
                          List<FormattedAddress> addressList,
                          List<FormattedAddress> privateAddressList,
                          List<FormattedAddress> businessAddressList,
                          List<SingleDrive> routeList) {

        this.routeCode = routeCode;
        this.addressList = addressList;
        this.privateAddressList = privateAddressList;
        this.businessAddressList = businessAddressList;
        this.routeList = routeList;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
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

    public List<SingleDrive> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<SingleDrive> routeList) {
        this.routeList = routeList;
    }
}

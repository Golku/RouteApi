package model.pojos;

import java.util.List;

public class Route {

    private String username;
    private String routeCode;
    private int routeState;
    private List<String> addressList;
    private List<FormattedAddress> formattedAddressList;
    private List<String> invalidAddressList;
    private int privateAddressCount;
    private int businessAddressCount;
    private int invalidAddressCount;
    private List<SingleDrive> routeList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public int getRouteState() {
        return routeState;
    }

    public void setRouteState(int routeState) {
        this.routeState = routeState;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    public List<FormattedAddress> getFormattedAddressList() {
        return formattedAddressList;
    }

    public void setFormattedAddressList(List<FormattedAddress> formattedAddressList) {
        this.formattedAddressList = formattedAddressList;
    }

    public int getPrivateAddressCount() {
        return privateAddressCount;
    }

    public void setPrivateAddressCount(int privateAddressCount) {
        this.privateAddressCount = privateAddressCount;
    }

    public int getBusinessAddressCount() {
        return businessAddressCount;
    }

    public void setBusinessAddressCount(int businessAddressCount) {
        this.businessAddressCount = businessAddressCount;
    }

    public int getInvalidAddressCount() {
        return invalidAddressCount;
    }

    public void setInvalidAddressCount(int invalidAddressCount) {
        this.invalidAddressCount = invalidAddressCount;
    }

    public List<SingleDrive> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<SingleDrive> routeList) {
        this.routeList = routeList;
    }

    public List<String> getInvalidAddressList() {
        return invalidAddressList;
    }

    public void setInvalidAddressList(List<String> invalidAddressList) {
        this.invalidAddressList = invalidAddressList;
    }
}

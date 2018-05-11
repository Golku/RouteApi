package model.pojos;

import java.util.List;

public class Container {

    private String username;
    private String routeCode;
    private int routeState;
    private List<Address> userAddressList;
    private List<Address> routeAddressList;
    private List<Drive> routeList;
    private int privateAddressCount;
    private int businessAddressCount;
    private int invalidAddressCount;

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

    public List<Address> getUserAddressList() {
        return userAddressList;
    }

    public void setUserAddressList(List<Address> userAddressList) {
        this.userAddressList = userAddressList;
    }

    public List<Address> getRouteAddressList() {
        return routeAddressList;
    }

    public void setRouteAddressList(List<Address> routeAddressList) {
        this.routeAddressList = routeAddressList;
    }

    public List<Drive> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Drive> routeList) {
        this.routeList = routeList;
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
}

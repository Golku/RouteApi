package model.pojos;

import java.util.List;

public class Container {

    private String username;
    private List<Address> addressList;
    private List<Drive> driveList;
    private int privateAddressCount;
    private int businessAddressCount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Drive> getDriveList() {
        return driveList;
    }

    public void setDriveList(List<Drive> driveList) {
        this.driveList = driveList;
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
}

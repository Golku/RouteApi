package model.pojos;

import java.util.HashMap;
import java.util.List;

public class Address {

    private boolean valid;
    private List<String> placeId;
    private String address;
    private String street;
    private String postCode;
    private String city;
    private String country;
    private double lat;
    private double lng;
    private int packageCount;
    private Notes notes;
    private boolean business;
    private String chosenBusinessName;
    private List<String> businessName;
    private HashMap<String, String[]> weekdayText;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getPlaceId() {
        return placeId;
    }

    public void setPlaceId(List<String> placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public boolean isBusiness() {
        return business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public String getChosenBusinessName() {
        return chosenBusinessName;
    }

    public void setChosenBusinessName(String chosenBusinessName) {
        this.chosenBusinessName = chosenBusinessName;
    }

    public List<String> getBusinessName() {
        return businessName;
    }

    public void setBusinessName(List<String> businessName) {
        this.businessName = businessName;
    }

    public HashMap<String, String[]> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(HashMap<String, String[]> weekdayText) {
        this.weekdayText = weekdayText;
    }
}

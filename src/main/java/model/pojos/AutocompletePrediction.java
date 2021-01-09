package model.pojos;

public class AutocompletePrediction {

    private String placeId;
    private String streetName;
    private String cityName;

    public AutocompletePrediction(String placeId, String streetName, String cityName) {
        this.placeId = placeId;
        this.streetName = streetName;
        this.cityName = cityName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}

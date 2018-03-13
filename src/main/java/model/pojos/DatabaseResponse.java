package model.pojos;

public class DatabaseResponse {

    private boolean error;
    private String errorMessage;
    private int business;
    private boolean informationAvailable;
    private TravelInformation travelInformation;

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isInformationAvailable() {
        return informationAvailable;
    }

    public void setInformationAvailable(boolean informationAvailable) {
        this.informationAvailable = informationAvailable;
    }

    public TravelInformation getTravelInformation() {
        return travelInformation;
    }

    public void setTravelInformation(TravelInformation travelInformation) {
        this.travelInformation = travelInformation;
    }
}

package model.pojos;

public class DbDriveInfo {

    private boolean error;
    private String message;
    private boolean infoAvailable;
    private String originAddress;
    private String destinationAddress;
    private String distanceHumanReadable;
    private long distanceInMeters;
    private String durationHumanReadable;
    private long durationInSeconds;
    private long refreshDate;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isInfoAvailable() {
        return infoAvailable;
    }

    public void setInfoAvailable(boolean infoAvailable) {
        this.infoAvailable = infoAvailable;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getDistanceHumanReadable() {
        return distanceHumanReadable;
    }

    public void setDistanceHumanReadable(String distanceHumanReadable) {
        this.distanceHumanReadable = distanceHumanReadable;
    }

    public long getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(long distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public String getDurationHumanReadable() {
        return durationHumanReadable;
    }

    public void setDurationHumanReadable(String durationHumanReadable) {
        this.durationHumanReadable = durationHumanReadable;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public long getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(long refreshDate) {
        this.refreshDate = refreshDate;
    }
}

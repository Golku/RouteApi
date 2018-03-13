package model.pojos;

public class SingleDrive {

    private FormattedAddress originFormattedAddress;
    private FormattedAddress destinationFormattedAddress;

    private String status;

    private boolean destinationIsABusiness;

    private String driveDurationHumanReadable;
    private long driveDurationInSeconds;

    private String driveDistanceHumanReadable;
    private long driveDistanceInMeters;

    private long deliveryTimeInMillis;
    private String deliveryTimeHumanReadable;

    public FormattedAddress getOriginFormattedAddress() {
        return originFormattedAddress;
    }

    public void setOriginFormattedAddress(FormattedAddress originFormattedAddress) {
        this.originFormattedAddress = originFormattedAddress;
    }

    public FormattedAddress getDestinationFormattedAddress() {
        return destinationFormattedAddress;
    }

    public void setDestinationFormattedAddress(FormattedAddress destinationFormattedAddress) {
        this.destinationFormattedAddress = destinationFormattedAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getDestinationIsABusiness() {
        return destinationIsABusiness;
    }

    public void setDestinationIsABusiness(boolean destinationIsABusiness) {
        this.destinationIsABusiness = destinationIsABusiness;
    }

    public long getDriveDurationInSeconds() {
        return driveDurationInSeconds;
    }

    public void setDriveDurationInSeconds(long driveDurationInSeconds) {
        this.driveDurationInSeconds = driveDurationInSeconds;
    }

    public String getDriveDurationHumanReadable() {
        return driveDurationHumanReadable;
    }

    public void setDriveDurationHumanReadable(String driveDurationHumanReadable) {
        this.driveDurationHumanReadable = driveDurationHumanReadable;
    }

    public long getDriveDistanceInMeters() {
        return driveDistanceInMeters;
    }

    public void setDriveDistanceInMeters(long driveDistanceInMeters) {
        this.driveDistanceInMeters = driveDistanceInMeters;
    }

    public String getDriveDistanceHumanReadable() {
        return driveDistanceHumanReadable;
    }

    public void setDriveDistanceHumanReadable(String driveDistanceHumanReadable) {
        this.driveDistanceHumanReadable = driveDistanceHumanReadable;
    }

    public long getDeliveryTimeInMillis() {
        return deliveryTimeInMillis;
    }

    public void setDeliveryTimeInMillis(long deliveryTimeInMillis) {
        this.deliveryTimeInMillis = deliveryTimeInMillis;
    }

    public String getDeliveryTimeHumanReadable() {
        return deliveryTimeHumanReadable;
    }

    public void setDeliveryTimeHumanReadable(String deliveryTimeHumanReadable) {
        this.deliveryTimeHumanReadable = deliveryTimeHumanReadable;
    }
}

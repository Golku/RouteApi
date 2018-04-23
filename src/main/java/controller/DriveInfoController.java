package controller;

import model.GoogleMapsApi;
import model.pojos.SingleDrive;
import model.pojos.SingleDriveRequest;

public class DriveInfoController {

    GoogleMapsApi googleMapsApi;

    public DriveInfoController() {
//        this.googleMapsApi = new GoogleMapsApi();
    }

    public SingleDrive getDriveInformation(SingleDriveRequest request){
        return googleMapsApi.getDriveInformation(request.getOrigin(), request.getDestination());
    }

}

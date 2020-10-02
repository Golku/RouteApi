package controller;

import model.AddressFormatter;
import model.DbManager;
import model.GoogleMapsApi;
import model.pojos.*;

import java.util.List;

public class DriveInfoController extends BaseController {

    private GoogleMapsApi googleMapsApi;
    private DbManager dbManager;
    private AddressFormatter addressFormatter;

    public DriveInfoController() {
        googleMapsApi = getGoogleMapsApi();
        dbManager = getDbManager();
        addressFormatter = getAddressFormatter();
    }

    public Drive getDriveInfo(DriveRequest request) {

        Drive drive = new Drive();
        drive.setOriginAddress(request.getOrigin());
        drive.setDestinationAddress(request.getDestination());

//        dbManager.getDriveInfo(drive);

//        if (!drive.isValid()) {
//            googleMapsApi.getDriveInfo(drive);
//            if (drive.isValid()) {
//                dbManager.addDriveInfo(drive);
//            }
//        }

        googleMapsApi.getDirections(drive);

        return drive;
    }
}

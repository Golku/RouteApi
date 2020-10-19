package controller;

import model.AddressFormatter;
import model.DbManager;
import model.GoogleMapsApi;
import model.pojos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriveInfoController extends BaseController {

    private static final List<Drive> drives = new ArrayList<>();

    private final GoogleMapsApi googleMapsApi;
    private final DbManager dbManager;
    private final AddressFormatter addressFormatter;

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

        for(Drive driveFromList : drives){
            if(driveFromList.getOriginAddress().equals(drive.getOriginAddress())){
                if(driveFromList.getDestinationAddress().equals(drive.getDestinationAddress())){
                    System.out.println("Directions from list");
                    drive = driveFromList;
                    break;
                }
            }
        }

        if (!drive.isValid()) {
            googleMapsApi.getDirections(drive);
            if (drive.isValid()) {
//                dbManager.addDriveInfo(drive);
                drives.add(drive);
            }
        }

        return drive;
    }
}

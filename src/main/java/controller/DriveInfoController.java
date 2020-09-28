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

    public void updateDriveList(UpdateDriveListRequest request){
        Container container = getContainerManager().getContainer(request.getUsername());
        container.setDriveList(request.getDriveList());

//        for(Drive drive : container.getDriveList()){
//            System.out.println("Drive from: " + drive.getOriginAddress().getAddress()+ " to " + drive.getDestinationAddress().getAddress());
//        }

    }

    public Drive getDriveInfo(DriveRequest request) {

        Drive drive = new Drive();

        drive.setOriginAddress(buildAddress(request.getOrigin()));
        drive.setDestinationAddress(buildAddress(request.getDestination()));

        dbManager.getDriveInfo(drive);

        if (!drive.isValid()) {
            googleMapsApi.getDriveInfo(drive);
            if (drive.isValid()) {
                dbManager.addDriveInfo(drive);
            }
        }

        if (drive.isValid()) {
            if (drive.getDestinationAddress().isBusiness()) {
                drive.setDestinationIsABusiness(true);
            }
        }

        return drive;
    }

    private Address buildAddress(String addressString) {

        Address address = new Address();
        address.setAddress(addressString);

        googleMapsApi.verifyAddress(address);

        if (address.isValid()) {
            addressFormatter.format(address);
        }

        if (address.isValid()) {
            dbManager.getAddressInfo(address);
        }

        return address;
    }
}

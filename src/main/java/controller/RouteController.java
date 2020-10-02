package controller;

import model.*;
import model.pojos.*;

import java.util.ArrayList;

public class RouteController extends BaseController{

    private ContainerManager containerManager;
    private DbManager dbManager;
    private GoogleMapsApi googleMapsApi;
    private AddressFormatter addressFormatter;

    private ArrayList<Address> tempAddressList;
    private ArrayList<Drive> organizedRoute;
    private boolean firstIteration;
    private Address origin;

    public RouteController() {
        containerManager = getContainerManager();
        googleMapsApi = getGoogleMapsApi();
        dbManager = getDbManager();
        addressFormatter = getAddressFormatter();
    }

    public RouteResponse organizedRoute(OrganizeRouteRequest request) {

        RouteResponse response = new RouteResponse();
        organizedRoute = new ArrayList<>();
        tempAddressList = new ArrayList<>();

        Container container = containerManager.getContainer(request.getUsername());

        Address startLocation = null;

        if(request.getRouteList().size() > 1){
            for(Address address : container.getAddressList()){
                if(request.getRouteList().get(request.getRouteList().size()-1).equals(address.getAddress())){
                    startLocation = address;
                }
            }
        }else{
            startLocation = buildAddress(request.getRouteList().get(0));
        }

        if(!startLocation.isValid()){
            return null;
        }

        tempAddressList.add(0, startLocation);

        for(Address address : container.getAddressList()){
            if (!request.getRouteList().contains(address.getAddress())){
                dbManager.getAddressInfo(address);
                tempAddressList.add(address);
            }
        }

        firstIteration = true;
        buildRoute(tempAddressList);

        response.setOrganizedRoute(organizedRoute);
        return response;
    }

    private void buildRoute(ArrayList<Address> addressesList){

        Drive currentDrive;
        Drive shortestDrive = null;
        Address currentAddress = null;

        if(firstIteration){
            origin = addressesList.get(0);
            addressesList.remove(origin);
        }

        for (Address address : addressesList){
            currentDrive = getDriveInfo(origin, address);
            if(shortestDrive == null){
                shortestDrive = currentDrive;
                currentAddress = address;
            }else{
                if(currentDrive.getDriveDistanceInMeters()<shortestDrive.getDriveDistanceInMeters()){
                    shortestDrive = currentDrive;
                    currentAddress = address;
                }
            }
        }

        origin = currentAddress;
        tempAddressList.remove(currentAddress);
        organizedRoute.add(shortestDrive);
        firstIteration = false;

        if(tempAddressList.size()>0){
            buildRoute(tempAddressList);
        }
    }

    private Drive getDriveInfo(Address origin, Address destination) {

//        Drive drive = new Drive();
//
//        drive.setOriginAddress(origin);
//        drive.setDestinationAddress(destination);
//
//        dbManager.getDriveInfo(drive);
//
//        if (!drive.isValid()) {
//            googleMapsApi.getDriveInfo(drive);
//            if (drive.isValid()) {
//                dbManager.addDriveInfo(drive);
//            }
//        }
//
//        if (drive.isValid()) {
//            if (drive.getDestinationAddress().isBusiness()) {
//                drive.setDestinationIsABusiness(true);
//            }
//        }
//
//        return drive;

        return null;
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

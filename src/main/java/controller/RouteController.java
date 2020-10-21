package controller;

import model.*;
import model.pojos.*;
import model.pojos.Address;
import model.pojos.graphhopper.*;

import java.util.ArrayList;
import java.util.List;

public class RouteController extends BaseController{

    private final GraphhopperApi graphhopperApi;
    private final ContainerManager containerManager;
    private final DbManager dbManager;
    private final GoogleMapsApi googleMapsApi;
    private final AddressFormatter addressFormatter;
    private ArrayList<Address> tempAddressList;
    private boolean firstIteration;
    private Address origin;

    private static final List<Drive> drives = new ArrayList<>();

    public RouteController() {
        containerManager = getContainerManager();
        googleMapsApi = getGoogleMapsApi();
        graphhopperApi = getGraphhopperApi();
        dbManager = getDbManager();
        addressFormatter = getAddressFormatter();
    }

    public OptimizedRoute organizedRoute(OrganizeRouteRequest request) {

        Container container = containerManager.getContainer(request.getUsername());

        OptimizedRoute optimizedRoute = new OptimizedRoute();
        ArrayList<Drive> organizedRoute = new ArrayList<>();
        optimizedRoute.setOrganizedRoute(organizedRoute);
        RouteOptimizationRequest routeOptimizationRequest = new RouteOptimizationRequest();
        RouteOptimizationResponse routeOptimizationResponse;
        List<Vehicle> vehicles = new ArrayList<>();
        List<Service> services = new ArrayList<>();
        routeOptimizationRequest.setVehicles(vehicles);
        routeOptimizationRequest.setServices(services);

        Address startLocation = buildAddress(request.getRouteList().get(0));
        optimizedRoute.setOriginAddress(startLocation);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id("my_vehicle");
        vehicle.setReturn_to_depot(false);
        StartAddress startAddress = new StartAddress();
        startAddress.setLocation_id(startLocation.getAddress());
        startAddress.setLat(startLocation.getLat());
        startAddress.setLon(startLocation.getLng());
        vehicle.setStart_address(startAddress);
        vehicles.add(vehicle);

        for(Address address : container.getAddressList()){
//            if(request.getRouteList().get(0).equals(address.getAddress())){
//                    System.out.println("Start location: " + address.getAddress());
//                    startLocation = address;
//            }else{
                Service service = new Service();
                service.setId(address.getAddress());
                service.setName("delivery for: "+address.getAddress());
                model.pojos.graphhopper.Address serviceAddress = new model.pojos.graphhopper.Address();
                serviceAddress.setLocation_id(address.getAddress());
                serviceAddress.setLat(address.getLat());
                serviceAddress.setLon(address.getLng());
                service.setAddress(serviceAddress);
                services.add(service);
//                System.out.println("Service for: " + address.getAddress());
//            }
        }

        routeOptimizationResponse = graphhopperApi.routeOptimization(routeOptimizationRequest);

        List<Activity> activityList = routeOptimizationResponse.getSolution().getRoutes().get(0).getActivities();

        for(Activity activity : activityList){
            if(activityList.indexOf(activity) != activityList.size()-1){
                Drive drive = new Drive();
                drive.setOriginAddress(activity.getAddress().getLocation_id());
                drive.setDestinationAddress(activityList.get(activityList.indexOf(activity)+1).getAddress().getLocation_id());

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
                        drives.add(drive);
                    }
                }

                organizedRoute.add(drive);
            }
//            System.out.println("Address: " + activity.getAddress().getLocation_id());
//            System.out.println("Type: " + activity.getType());
        }

        return optimizedRoute;
    }

    public OptimizedRoute organizedRouteShortestDistance(OrganizeRouteRequest request) {

        OptimizedRoute response = new OptimizedRoute();
//        organizedRoute = new ArrayList<>();
//        tempAddressList = new ArrayList<>();
//
//        Container container = containerManager.getContainer(request.getUsername());
//
//        Address startLocation = null;
//
//        if(request.getRouteList().size() > 1){
//            for(Address address : container.getAddressList()){
//                if(request.getRouteList().get(request.getRouteList().size()-1).equals(address.getAddress())){
//                    startLocation = address;
//                }
//            }
//        }else{
//            startLocation = buildAddress(request.getRouteList().get(0));
//        }
//
//        if(!startLocation.isValid()){
//            return null;
//        }
//
//        tempAddressList.add(0, startLocation);
//
//        for(Address address : container.getAddressList()){
//            if (!request.getRouteList().contains(address.getAddress())){
//                dbManager.getAddressInfo(address);
//                tempAddressList.add(address);
//            }
//        }
//
//        firstIteration = true;
//        buildRoute(tempAddressList);
//
//        response.setOrganizedRoute(organizedRoute);
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
//        organizedRoute.add(shortestDrive);
        firstIteration = false;

        if(tempAddressList.size()>0){
            buildRoute(tempAddressList);
        }
    }

    private Drive getDriveInfo(Address origin, Address destination) {

        Drive drive = new Drive();

        drive.setOriginAddress(origin.getAddress());
        drive.setDestinationAddress(destination.getAddress());

//        dbManager.getDriveInfo(drive);

        if (!drive.isValid()) {
            googleMapsApi.getDirections(drive);
//            if (drive.isValid()) {
//                dbManager.addDriveInfo(drive);
//            }
        }

//        if (drive.isValid()) {
//            if (drive.getDestinationAddress().isBusiness()) {
//                drive.setDestinationIsABusiness(true);
//            }
//        }

        return drive;
    }

    private Address buildAddress(String addressString) {

        Address address = new Address();
        address.setAddress(addressString);

        address.setBusiness(false);

        System.out.println("buildAddress from RouteController");

        graphhopperApi.geocodeAddress(address);

        if(!address.isValid()){
            googleMapsApi.geocodeAddress(address);
        }

        if (address.isValid()) {
            addressFormatter.format(address);
        }

        if (address.isValid()) {
            dbManager.getAddressInfo(address);
        }

        return address;
    }
}

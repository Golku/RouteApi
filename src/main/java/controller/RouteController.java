package controller;

import model.*;
import model.pojos.*;
import model.pojos.Address;
import model.pojos.graphhopper.*;
import model.pojos.openrouteservice.Step;

import java.util.ArrayList;
import java.util.List;

public class RouteController extends BaseController{

    private final GraphhopperApi graphhopperApi;
    private final OpenRouteServiceApi openRouteServiceApi;
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
        openRouteServiceApi = getOpenRouteServiceApi();
        graphhopperApi = getGraphhopperApi();
        dbManager = getDbManager();
        addressFormatter = getAddressFormatter();
    }

    private Address findAddress(String addressString, List<Address> addresses){
        Address address = null;
        for(Address it : addresses){
            if(it.getAddress().equals(addressString)){
                address = it;
                break;
            }
        }
        return address;
    }

    public OptimizedRoute optimizeRouteOpenRouteService(OrganizeRouteRequest request){

        Container container = containerManager.getContainer(request.getUsername());

        OptimizedRoute optimizedRoute = new OptimizedRoute();
        ArrayList<Drive> optimizedStops = new ArrayList<>();
        optimizedRoute.setOrganizedRoute(optimizedStops);

        Address startLocation = null;
        Address endLocation = null;
        List<Address> stopList = new ArrayList<>();

        for(Address address: container.getAddressList()){
            if(request.getStartLocation().contains(address.getAddress())){
                if(request.getStartLocation().contains(address.getCity())){
                    startLocation = address;
                    printLn("startLoc from list");
                    break;
                }
            }
        }

        if(startLocation == null){
            startLocation = buildAddress(request.getStartLocation());
        }

        if(!request.getEndLocation().equals("best_time")){
            endLocation = buildAddress(request.getEndLocation());
            optimizedRoute.setEndLocation(endLocation);
        }

        for(String address: request.getRouteList()){
            stopList.add(findAddress(address, container.getAddressList()));
        }

        model.pojos.openrouteservice.RouteOptimizationResponse routeOptimizationResponse;

        routeOptimizationResponse = openRouteServiceApi.optimizedRoute(startLocation, endLocation, stopList);

        List<Step> steps = routeOptimizationResponse.getRoutes().get(0).getSteps();

        for(Step step : steps){

            if(steps.indexOf(step) != steps.size()-1){

                Drive drive = new Drive();

                switch (step.getType()){
                    case "start":
                        drive.setOriginAddress(startLocation.getAddress());
                        drive.setDestinationAddress(stopList.get(steps.get(1).getId()).getAddress());
                        break;
                    case "job":
                        drive.setOriginAddress(stopList.get(step.getId()).getAddress());

                        if(!steps.get(steps.indexOf(step)+1).getType().equals("end")){
                            drive.setDestinationAddress(stopList.get(steps.get(steps.indexOf(step)+1).getId()).getAddress());
                        }else{
                            drive.setDestinationAddress(endLocation.getAddress());
                        }
                        break;
                }

                for(Drive driveFromList : drives){
                        if(driveFromList.getOriginAddress().equals(drive.getOriginAddress())){
                            if(driveFromList.getDestinationAddress().equals(drive.getDestinationAddress())){
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

                optimizedStops.add(drive);
            }

        }

        return optimizedRoute;
    }

    public OptimizedRoute optimizeRouteGraphhopper(OrganizeRouteRequest request) {

        Container container = containerManager.getContainer(request.getUsername());

        OptimizedRoute optimizedRoute = new OptimizedRoute();
        ArrayList<Drive> optimizedStops = new ArrayList<>();
        optimizedRoute.setOrganizedRoute(optimizedStops);

        Address startLocation = null;
        Address endLocation = null;
        List<Address> stopList = new ArrayList<>();

        for(Address address: container.getAddressList()){
            if(request.getStartLocation().contains(address.getAddress())){
                if(request.getStartLocation().contains(address.getCity())){
                    startLocation = address;
                    printLn("startLoc from list");
                    break;
                }
            }
        }

        if(startLocation == null){
            startLocation = buildAddress(request.getStartLocation());
        }

        if(!request.getEndLocation().equals("best_time")){
            endLocation = buildAddress(request.getEndLocation());
            optimizedRoute.setEndLocation(endLocation);
        }

        for(String address: request.getRouteList()){
            stopList.add(findAddress(address, container.getAddressList()));
        }

        RouteOptimizationResponse routeOptimizationResponse;

        routeOptimizationResponse = graphhopperApi.optimizedRoute(startLocation, endLocation, stopList);

        List<Activity> activityList = routeOptimizationResponse.getSolution().getRoutes().get(0).getActivities();

        for(Activity activity : activityList){
            if(activityList.indexOf(activity) != activityList.size()-1){
                Drive drive = new Drive();
                drive.setOriginAddress(activity.getAddress().getLocation_id());
                drive.setDestinationAddress(activityList.get(activityList.indexOf(activity)+1).getAddress().getLocation_id());

                for(Drive driveFromList : drives){
                    if(driveFromList.getOriginAddress().equals(drive.getOriginAddress())){
                        if(driveFromList.getDestinationAddress().equals(drive.getDestinationAddress())){
//                            System.out.println("Directions from list");
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

                optimizedStops.add(drive);
            }
        }

        return optimizedRoute;
    }

    public OptimizedRoute optimizedRouteClosestAddress(OrganizeRouteRequest request) {

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

//        System.out.println("buildAddress from RouteController");

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

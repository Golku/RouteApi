package controller;

import model.*;
import model.pojos.*;

import java.util.ArrayList;
import java.util.List;

public class RouteController extends BaseController{

    /*
     * routeState 0 = Route does not exist
     * routeState 1 = Route was submitted with no addresses
     * routeState 2 = Validating the addresses
     * routeState 3 = Route is being organized
     * routeState 4 = Route is ready
     * */

    private RouteManager routeManager;
    private ContainerManager containerManager;
    private DbManager dbManager;
    private GoogleMapsApi googleMapsApi;
    private AddressFormatter addressFormatter;

    private Route userRoute;

    public RouteController() {
        routeManager = getRouteManager();
        containerManager = getContainerManager();
        googleMapsApi = getGoogleMapsApi();
        dbManager = getDbManager();
        addressFormatter = getAddressFormatter();
    }

    public Route fetchRoute(String username) {
        Route route = routeManager.getRoute(username);

        if (route == null) {
            route = new Route();
            route.setRouteState(0);
        }

        return route;
    }

    public void createRoute(IncomingRoute route) {

        //temporary
        route.setRouteCode("1");
        //log this list
        //route.getAddressList();

        if (route.getRouteCode().isEmpty() || route.getUsername().isEmpty()) {
            System.out.println("No userRoute identifier provided");
            //log occurrences of this case
            return;
        }

        routeManager.createRoute(route.getUsername(), route.getRouteCode());

        userRoute = routeManager.getRoute(route.getUsername());

        containerManager.addRoute(route.getUsername(), userRoute);

        if (route.getAddressList() == null || route.getAddressList().isEmpty()) {
            System.out.println("Route address list is empty");
            userRoute.setRouteState(1);
            System.out.println("routeState: " + userRoute.getRouteState());
            return;
        }

        validateAddressList(route.getAddressList());
    }

    public void correctedAddresses(CorrectedAddresses correctedAddresses) {

        if (correctedAddresses.getUsername().isEmpty()) {
            System.out.println("No userRoute identifier provided");
            return;
        }

        userRoute = routeManager.getRoute(correctedAddresses.getUsername());

        if (correctedAddresses.getCorrectedAddressesList().size() > 0) {
//            addressValidation(2, correctedAddresses.getCorrectedAddressesList());
        }
    }

    private void validateAddressList(List<String> addressList) {

        userRoute.setRouteState(2);

        System.out.println("addressValidation");
        System.out.println("routeState: " + userRoute.getRouteState());

        List<Address> addresses = new ArrayList<>();

        for(String addressString : addressList){
            Address address = new Address();

            address.setAddress(addressString);

            googleMapsApi.verifyAddress(address);

            addresses.add(address);
        }

        userRoute.setAddressList(addresses);

        formatAddressList();
    }

    private void formatAddressList(){
        for(Address address: userRoute.getAddressList()){
            if(address.isValid()) {
                addressFormatter.format(address);
            }
        }
        sortAddressList();
    }

    private void sortAddressList(){

        for(Address address: userRoute.getAddressList()){
            if(address.isValid()) {
                dbManager.getAddressInfo(address);
            }
        }

        userRoute.setRouteState(4);
        System.out.println("routeState: " + userRoute.getRouteState());
    }

    private void organizedRoute() {
//        userRoute.setRouteState(5);
//        System.out.println("organizedRoute");
//        System.out.println("routeState: " + userRoute.getRouteState());
//
//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }
//
//        List<Drive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(userRoute);
//
//        System.out.println("Organized userRoute size: " + organizedRouteList.size());
//
////        fix this to check for a null object instead of the size
//        if (organizedRouteList.size() > 0) {
//            userRoute.setRouteState(7);
//            System.out.println("routeState: " + userRoute.getRouteState());
//        } else {
//            userRoute.setRouteState(6);
//            System.out.println("routeState: " + userRoute.getRouteState());
//        }
//
    }
}

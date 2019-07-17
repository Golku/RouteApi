package controller;

import model.*;
import model.pojos.*;
import okhttp3.Route;

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

    private ContainerManager containerManager;
    private DbManager dbManager;
    private GoogleMapsApi googleMapsApi;
    private AddressFormatter addressFormatter;

    private Container container;

    public RouteController() {
        containerManager = getContainerManager();
        googleMapsApi = getGoogleMapsApi();
        dbManager = getDbManager();
        addressFormatter = getAddressFormatter();
    }

    public void createRoute(IncomingRoute route) {

        if (route.getUsername().isEmpty()) {
            System.out.println("No userRoute identifier provided");
            //log occurrences of this case
            return;
        }

        container = containerManager.getContainer(route.getUsername());

        if (route.getAddressList() == null || route.getAddressList().isEmpty()) {
            System.out.println("Route address list is empty");
            container.setRouteState(1);
            System.out.println("routeState: " + container.getRouteState());
            return;
        }

        validateAddressList(route.getAddressList());
    }

    private void validateAddressList(List<String> addressList) {

        container.setRouteState(2);

        System.out.println("addressValidation");
        System.out.println("routeState: " + container.getRouteState());

        for(String addressString : addressList){
            Address address = new Address();

            address.setAddress(addressString);

            googleMapsApi.verifyAddress(address);

            if(address.isValid()) {
                addressFormatter.format(address);
            }

            if(address.isValid()) {
                dbManager.getAddressInfo(address);
            }

            containerManager.putAddressInList(container.getUsername(), address);
        }

        container.setRouteState(4);
        System.out.println("routeState: " + container.getRouteState());
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

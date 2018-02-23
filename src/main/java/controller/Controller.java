package controller;

import model.*;
import model.pojos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {

    private RoutesManager routesManager;
    private RoutesOrganizer routesOrganizer;
    private AddressFormatter addressFormatter;
    private GoogleMapsApi googleMapsApi;
    private AddressesInformationManager addressesInformationManager;

    public Controller() {
        this.addressFormatter = new AddressFormatter();
        this.googleMapsApi = new GoogleMapsApi(this.addressFormatter);
        this.routesManager = new RoutesManager();
        this.routesOrganizer = new RoutesOrganizer(this.googleMapsApi);
        this.addressesInformationManager = new AddressesInformationManager(this.googleMapsApi);
    }

    public Response getRoute (String routeCode){

        Response response = new Response();

        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(routeCode);

        //If the client ask for the route while the api is running validateAddressList
        //unOrganizedRoute will be null and the client will give a this route does not exist error. Fix This!!
        //Fix: Make the unOrganizedRoute before the addresses are validated.
        if(unOrganizedRoute != null) {
            if (unOrganizedRoute.isOrganizingInProgress()) {
                response.setOrganizingInProgress(true);
            } else {

                response.setOrganizingInProgress(false);

                if (unOrganizedRoute.getWrongAddressesList().size() > 0) {
                    response.setRouteHasInvalidAddresses(true);

                    ArrayList<String> invalidAddresses = new ArrayList<>();

                    for(int i=0; i<unOrganizedRoute.getWrongAddressesList().size(); i++){
                        invalidAddresses.add(unOrganizedRoute.getWrongAddressesList().get(i).getRawAddress());
                    }

                    response.setInvalidAddresses(invalidAddresses);

                } else {
                    response.setOrganizedRoute(routesManager.getOrganizedRoute(routeCode));
                }
            }
        }else{
            response.setRouteIsNull(true);
        }
        return response;
    }

    public void calculateRoute(IncomingRoute route){

        validateAddressList(route.getRouteCode(), route.getAddressList());

        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(route.getRouteCode());

        if(unOrganizedRoute.getWrongAddressesList().size()>0){
            unOrganizedRoute.setOrganizingInProgress(false);
        }else{
            unOrganizedRoute.setOrigin(route.getOrigin());
            organizeRoute(unOrganizedRoute);
        }
//        System.out.println("Validate addresses list size: " +unOrganizedRoute.getAllValidatedAddressesList().size());
//        System.out.println("Invalid addresses list size: "+ unOrganizedRoute.getWrongAddressesList().size());
//
//        System.out.println("");
//
//        System.out.println("Valid");
//        for(int i = 0; i<unOrganizedRoute.getAllValidatedAddressesList().size(); i++){
//            System.out.println(unOrganizedRoute.getAllValidatedAddressesList().get(i).getRawAddress());
//            System.out.println(unOrganizedRoute.getAllValidatedAddressesList().get(i).getFormattedAddress());
//        }
//        System.out.println("Wrong");
//        for(int i = 0; i<unOrganizedRoute.getWrongAddressesList().size(); i++){
//            System.out.println(unOrganizedRoute.getWrongAddressesList().get(i).getRawAddress());
//        }
    }

    public void validateAddressList(String routeCode, List<String> addressesList) {

        Map<String, List<FormattedAddress>> validatedAddressLists;

        validatedAddressLists = addressesInformationManager.validateAddresses(addressesList);

        validatedAddressLists.put("businessAddresses", addressesInformationManager.findBusinessAddresses());

        validatedAddressLists.put("privateAddresses", addressesInformationManager.findPrivateAddresses());

        createUnorganizedRoute(routeCode, validatedAddressLists);
    }

    public void createUnorganizedRoute(String routeCode, Map<String, List<FormattedAddress>> validatedAddressLists){
        routesManager.createUnorganizedRoute(routeCode, validatedAddressLists);
    }

    public void organizeRoute(UnOrganizedRoute unOrganizedRoute){

        unOrganizedRoute.setOrganizingInProgress(true);

        routesOrganizer.setOrigin(unOrganizedRoute.getOrigin());

        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(unOrganizedRoute);

        routesManager.createOrganizedRoute(unOrganizedRoute.getRouteCode(), organizedRouteList);

        unOrganizedRoute.setOrganizingInProgress(false);

//        System.out.println("private addresses: " + singleOrganizedRoute.getPrivateAddressesCount());
//        System.out.println("business addresses: " + singleOrganizedRoute.getBusinessAddressesCount());
//        System.out.println("wrong addresses: " + singleOrganizedRoute.getWrongAddressesCount());
//
//        System.out.println();
//
//        for(int i=0; i<singleOrganizedRoute.getRouteList().size(); i++){
//            System.out.println(singleOrganizedRoute.getRouteList().get(i).getOriginFormattedAddress().getCompletedAddress());
//            System.out.println(singleOrganizedRoute.getRouteList().get(i).getDestinationFormattedAddress().getCompletedAddress());
//            System.out.println(singleOrganizedRoute.getRouteList().get(i).getDriveDurationHumanReadable());
//            System.out.println(singleOrganizedRoute.getRouteList().get(i).getDriveDistanceHumanReadable());
//            System.out.println("Business: " + singleOrganizedRoute.getRouteList().get(i).getDestinationIsABusiness());
//            System.out.println("Estimated delivery Time: " + singleOrganizedRoute.getRouteList().get(i).getDeliveryTimeHumanReadable());
//            System.out.println();
//        }
    }

}

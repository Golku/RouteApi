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

        if(routesManager.getRoutesOrganizingState(routeCode)){
            response.setOrganizingInProgress(true);
        }else{
            response.setOrganizingInProgress(false);
            response.setOrganizedRoute(routesManager.getSingleOrganizedRoute(routeCode));
        }

        return response;
    }

    public void calculateRoute(IncomingRoute route){

        routesManager.setRoutesOrganizingState(route.getRouteCode(), true);

        validateAddressList(route.getRouteCode(), route.getAddressList());

        UnOrganizedRoute unOrganizedRoute = routesManager.getSingleUnorganizedRoute(route.getRouteCode());

        unOrganizedRoute.setOrigin(route.getOrigin());

        if(unOrganizedRoute.getWrongAddressesList().size()>0){
            System.out.println("There are wrong addresses: "+unOrganizedRoute.getWrongAddressesList().size());
            routesManager.setRoutesOrganizingState(unOrganizedRoute.getRouteCode(), false);
        }else{
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

        routesOrganizer.setOrigin(unOrganizedRoute.getOrigin());

        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(unOrganizedRoute);

        routesManager.createOrganizedRoute(unOrganizedRoute.getRouteCode(), organizedRouteList);

        routesManager.setRoutesOrganizingState(unOrganizedRoute.getRouteCode(), false);
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

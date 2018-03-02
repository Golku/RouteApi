package controller;

import model.*;
import model.pojos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubmitRouteController {

    private RoutesManager routesManager;
    private RoutesOrganizer routesOrganizer;
    private AddressesInformationManager addressesInformationManager;

    public SubmitRouteController() {
        AddressFormatter addressFormatter = new AddressFormatter();
        GoogleMapsApi googleMapsApi = new GoogleMapsApi(addressFormatter);
        this.routesManager = new RoutesManager();
        this.routesOrganizer = new RoutesOrganizer(googleMapsApi);
        this.addressesInformationManager = new AddressesInformationManager(googleMapsApi);
    }

    public void calculateRoute(IncomingRoute route){

        routesManager.createUnorganizedRoute(route.getRouteCode());

        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(route.getRouteCode());

        unOrganizedRoute.setAddressValidatingInProgress(true);

        unOrganizedRoute.setOrganizingInProgress(true);

        validateAddressList(unOrganizedRoute, route.getAddressList());

        unOrganizedRoute.setAddressValidatingInProgress(false);

        if(unOrganizedRoute.getWrongAddressesList().size()>0){
            unOrganizedRoute.setOrganizingInProgress(false);
            unOrganizedRoute.setHasInvalidAddresses(true);
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

    private void validateAddressList(UnOrganizedRoute unOrganizedRoute, List<String> addressesList) {

        Map<String, List<FormattedAddress>> validatedAddressLists;

        validatedAddressLists = addressesInformationManager.validateAddresses(addressesList);

        validatedAddressLists.put("businessAddresses", addressesInformationManager.findBusinessAddresses());

        validatedAddressLists.put("privateAddresses", addressesInformationManager.findPrivateAddresses());

        unOrganizedRoute.setAllValidatedAddressesList(validatedAddressLists.get("validAddresses"));
        unOrganizedRoute.setPrivateAddressList(validatedAddressLists.get("privateAddresses"));
        unOrganizedRoute.setBusinessAddressList(validatedAddressLists.get("businessAddresses"));
        unOrganizedRoute.setWrongAddressesList(validatedAddressLists.get("wrongAddresses"));
    }

    private void organizeRoute(UnOrganizedRoute unOrganizedRoute){

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

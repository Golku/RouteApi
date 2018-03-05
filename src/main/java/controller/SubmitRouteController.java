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

        unOrganizedRoute.setOrigin(route.getOrigin());

        validateAddressList("create", unOrganizedRoute, route.getAddressList());

        if(unOrganizedRoute.getWrongAddressesList().size()>0){
            unOrganizedRoute.setHasInvalidAddresses(true);
            unOrganizedRoute.setAddressValidatingInProgress(false);
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

    public void checkSubmittedAddresses(CorrectedAddresses correctedAddresses){

        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(correctedAddresses.getRouteCode());

        unOrganizedRoute.setAddressValidatingInProgress(true);

        System.out.println(correctedAddresses.getCorrectedAddressesList().size());

        if(correctedAddresses.getCorrectedAddressesList().size()>0) {
            validateAddressList("add", unOrganizedRoute, correctedAddresses.getCorrectedAddressesList());
        }else{
            unOrganizedRoute.getWrongAddressesList().clear();
        }

        if(unOrganizedRoute.getWrongAddressesList().size()>0){
            unOrganizedRoute.setHasInvalidAddresses(true);
            unOrganizedRoute.setAddressValidatingInProgress(false);
        }else{
            organizeRoute(unOrganizedRoute);
        }

    }

    private void validateAddressList(String type, UnOrganizedRoute unOrganizedRoute, ArrayList<String> addressesList) {

        Map<String, List<FormattedAddress>> validatedAddressLists = addressesInformationManager.validateAddresses(addressesList);

        if(type.equals("create")){
            unOrganizedRoute.setAllValidatedAddressesList(validatedAddressLists.get("validAddresses"));
        }else if(type.equals("add")){
            List<FormattedAddress> currentValidatedAddressList = validatedAddressLists.get("validAddresses");
            List<FormattedAddress> routeValidatedAddressList = unOrganizedRoute.getAllValidatedAddressesList();
            routeValidatedAddressList.addAll(currentValidatedAddressList);
        }

        unOrganizedRoute.setWrongAddressesList(validatedAddressLists.get("wrongAddresses"));

    }

    private void sortAddressList(UnOrganizedRoute unOrganizedRoute){

        List<FormattedAddress> validatedAddressList = unOrganizedRoute.getAllValidatedAddressesList();

        List<FormattedAddress> businessAddresses = addressesInformationManager.findBusinessAddresses(validatedAddressList);
        List<FormattedAddress> privateAddresses = addressesInformationManager.findPrivateAddresses(validatedAddressList);

        unOrganizedRoute.setBusinessAddressList(businessAddresses);
        unOrganizedRoute.setPrivateAddressList(privateAddresses);
    }

    private void organizeRoute(UnOrganizedRoute unOrganizedRoute){

        unOrganizedRoute.setHasInvalidAddresses(false);
        unOrganizedRoute.setOrganizingInProgress(true);
        unOrganizedRoute.setAddressValidatingInProgress(false);

        sortAddressList(unOrganizedRoute);

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
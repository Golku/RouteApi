package controller;

import model.*;
import model.pojos.FormattedAddress;
import model.pojos.SingleDrive;
import model.pojos.SingleOrganizedRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {

    private RoutesManager routesManager;
    private RoutesOrganizer routesOrganizer;
    private AddressFormatter addressFormatter;
    private GoogleMapsApi googleMapsApi;
    private AddressesInformationManager addressesInformationManager;

    public MainController() {
        this.addressFormatter = new AddressFormatter();
        this.googleMapsApi = new GoogleMapsApi(this.addressFormatter);
        this.routesManager = new RoutesManager();
        this.routesOrganizer = new RoutesOrganizer(this.googleMapsApi);
        this.addressesInformationManager = new AddressesInformationManager(this.googleMapsApi);
    }

    public void getUnorganizedRoute(String routecode) {

        int size = routesManager.getSingleUnorganizedRoute(routecode).getAllValidatedAddressesList().size();

        for(int i=0; i<size; i++) {
            System.out.println(routesManager.getSingleUnorganizedRoute(routecode).getAllValidatedAddressesList().get(i).getRawAddress());
            System.out.println(routesManager.getSingleUnorganizedRoute(routecode).getAllValidatedAddressesList().get(i).getFormattedAddress());
        }
    }

    public void getOrganizedRoute(String routecode) {

        int size = routesManager.getSingleUnorganizedRoute(routecode).getAllValidatedAddressesList().size();

        for(int i=0; i<size; i++) {
            //System.out.println(routesManager.getSingleOrganizedRoute(routecode).getRouteList().get(i).getCompletedAddress());
        }
    }

    public void validateAddressList(String routeCode, List<String> addressesList) {

        Map<String, List<FormattedAddress>> validatedAddressLists;

        List<FormattedAddress> addressListFormatted = new ArrayList<>();

        for(int i = 0; i<addressesList.size(); i++){
            addressListFormatted.add(addressFormatter.formatAddress(addressesList.get(i)));
        }

        validatedAddressLists = addressesInformationManager.validateAddresses(addressListFormatted);

        validatedAddressLists.put("businessAddresses", addressesInformationManager.findBusinessAddresses());

        validatedAddressLists.put("privateAddresses", addressesInformationManager.findPrivateAddresses());

        createUnorganizedRoute(routeCode, validatedAddressLists);
    }

    public void createUnorganizedRoute(String routeCode, Map<String, List<FormattedAddress>> validatedAddressLists){
        routesManager.createUnorganizedRoute(routeCode, validatedAddressLists);
    }

    public SingleOrganizedRoute organizeRoute(String routeCode, String origin){

        routesOrganizer.setOrigin(origin);

        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(routesManager.getSingleUnorganizedRoute(routeCode));

        routesManager.createOrganizedRoute(routeCode, organizedRouteList);

        SingleOrganizedRoute singleOrganizedRoute = routesManager.getSingleOrganizedRoute(routeCode);

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

        return singleOrganizedRoute;

    }

}

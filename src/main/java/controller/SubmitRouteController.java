package controller;

import com.google.gson.Gson;
import model.*;
import model.pojos.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubmitRouteController {

    private RoutesManager routesManager;
    private RoutesOrganizer routesOrganizer;
    private AddressesInformationManager addressesInformationManager;

    /*
    * routeState 1 = validatingAddresses
    * routeState 2 = organizingRoute
    * routeState 3 = routeOrganized
    * routeState 4 = hasInvalidAddresses
    * routeState 5 = invalidRoute
    * */

    public SubmitRouteController() {

        AddressFormatter addressFormatter = new AddressFormatter();
        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)//192.168.0.16
                .baseUrl("http://192.168.0.16/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = retrofitBuilder.build();
        DatabaseService databaseService = retrofit.create(DatabaseService.class);

        GoogleMapsApi googleMapsApi = new GoogleMapsApi(addressFormatter, databaseService);
        this.routesManager = new RoutesManager();
        this.routesOrganizer = new RoutesOrganizer(googleMapsApi);
        this.addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService);

    }

//    public void calculateRoute(IncomingRoute route){
//
//        routesManager.createUnorganizedRoute(route.getRouteCode());
//
//        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(route.getRouteCode());
//
//        unOrganizedRoute.setRouteState(1);
//
//        unOrganizedRoute.setOrigin(route.getOrigin());
//
//        validateAddressList("create", unOrganizedRoute, route.getAddressList());
//
//        if(unOrganizedRoute.getWrongAddressesList().size()>0){
//            unOrganizedRoute.setRouteState(4);
//        }else{
//            organizeRoute(unOrganizedRoute);
//        }
//    }

    public void checkSubmittedAddresses(CorrectedAddresses correctedAddresses){

//        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(correctedAddresses.getRouteCode());
//
//        unOrganizedRoute.setRouteState(1);
//
//        if(correctedAddresses.getCorrectedAddressesList().size()>0) {
//            validateAddressList("add", unOrganizedRoute, correctedAddresses.getCorrectedAddressesList());
//        }else{
//            unOrganizedRoute.getWrongAddressesList().clear();
//        }
//
//        if(unOrganizedRoute.getWrongAddressesList().size()>0){
//            unOrganizedRoute.setRouteState(4);
//        }else{
//            organizeRoute(unOrganizedRoute);
//        }

    }

    private void validateAddressList(String type, UnOrganizedRoute unOrganizedRoute, ArrayList<String> addressesList) {

        Map<String, List<FormattedAddress>> validatedAddressLists = addressesInformationManager.validateAddressList(addressesList);

        if(type.equals("create")){
            unOrganizedRoute.setAllValidAddressesList(validatedAddressLists.get("validAddresses"));
        }else if(type.equals("add")){
            List<FormattedAddress> currentValidatedAddressList = validatedAddressLists.get("validAddresses");
            List<FormattedAddress> routeValidatedAddressList = unOrganizedRoute.getAllValidAddressesList();
            routeValidatedAddressList.addAll(currentValidatedAddressList);
        }

//        unOrganizedRoute.setWrongAddressesList(validatedAddressLists.get("wrongAddresses"));

    }

    private void organizeRoute(UnOrganizedRoute unOrganizedRoute){

//        unOrganizedRoute.setRouteState(2);
//
//        sortAddressList(unOrganizedRoute);
//
//        routesOrganizer.setOrigin(unOrganizedRoute.getOrigin());
//
//        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(unOrganizedRoute);
//
//        routesManager.createOrganizedRoute(unOrganizedRoute.getRouteCode(), organizedRouteList);
//
//        unOrganizedRoute.setRouteState(3);

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

    private void sortAddressList(UnOrganizedRoute unOrganizedRoute){

//        List<FormattedAddress> validatedAddressList = unOrganizedRoute.getAllValidAddressesList();
//
//        List<FormattedAddress> businessAddresses = addressesInformationManager.findBusinessAddresses(validatedAddressList);
//        List<FormattedAddress> privateAddresses = addressesInformationManager.findPrivateAddresses(validatedAddressList);
//
//        unOrganizedRoute.setBusinessAddressList(businessAddresses);
//        unOrganizedRoute.setPrivateAddressList(privateAddresses);
    }

}

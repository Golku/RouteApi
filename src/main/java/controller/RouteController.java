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

public class RouteController {

    /*
     * routeState 0 = Route does not exist
     * routeState 1 = Route was submitted with no addresses
     * routeState 2 = Validating the addresses
     * routeState 3 = Route is being organized
     * routeState 4 = Route can not be organized
     * routeState 5 = Route is ready
     * */

    private RouteManager routeManager;
    private RoutesOrganizer routesOrganizer;
    private AddressesInformationManager addressesInformationManager;

    private Route userRoute;

    public RouteController() {
        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)//192.168.0.16
                .baseUrl("http://192.168.0.16/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        DatabaseService databaseService = retrofit.create(DatabaseService.class);

        GoogleMapsApi googleMapsApi = new GoogleMapsApi(databaseService);
        routeManager = new RouteManager();
        routesOrganizer = new RoutesOrganizer(googleMapsApi);
        addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService);
    }

    public Route fetchRoute(String username){
        Route route = routeManager.getRoute(username);

        if(route == null){
            route = new Route();
            route.setRouteState(0);
        }

        return route;
    }

    public void createRoute(IncomingRoute route){

        if(route.getRouteCode().isEmpty() || route.getUsername().isEmpty()){
            System.out.println("No userRoute identifier provided");
            //log occurrences of this case
            return;
        }

        routeManager.createRoute(route.getUsername(), route.getRouteCode());
        userRoute = routeManager.getRoute(route.getUsername());

        if(route.getAddressList().size() <= 0){
            System.out.println("Route address list is empty");
            userRoute.setRouteState(1);
            System.out.println("routeState: " + userRoute.getRouteState());
            return;
        }

        userRoute.setAddressList(route.getAddressList());

        System.out.println("username: " + userRoute.getUsername());
        System.out.println("routeCode: " + userRoute.getRouteCode());
        addressValidation(1, userRoute.getAddressList());
    }

    public void correctedAddresses(CorrectedAddresses correctedAddresses){

        if(correctedAddresses.getUsername().isEmpty()){
            System.out.println("No userRoute identifier provided");
            return;
        }

        userRoute = routeManager.getRoute(correctedAddresses.getUsername());

        if(correctedAddresses.getCorrectedAddressesList().size()>0) {
            addressValidation(2, correctedAddresses.getCorrectedAddressesList());
        }
    }

    private void addressValidation(int action, List<String> addressList){

        userRoute.setRouteState(3);

        System.out.println("addressValidation");

        System.out.println("routeState: " + userRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

//        The formattedAddress list can be returned empty. Find a way to FIX THIS!!!
        Map<String, List<FormattedAddress>> validatedAddressMap = addressesInformationManager.validateAddressList(addressList);
        List<FormattedAddress> validAddresses = validatedAddressMap.get("validAddresses");
        List<FormattedAddress> invalidAddresses = validatedAddressMap.get("invalidAddresses");

        if(action == 1){
            userRoute.setFormattedAddressList(validAddresses);
        }else if(action == 2){
            userRoute.getFormattedAddressList().addAll(validAddresses);
        }

        for(FormattedAddress validAddress : userRoute.getFormattedAddressList()){
            System.out.println("valid address: " + validAddress.getFormattedAddress());
        }

        if(invalidAddresses.size()>0){
            userRoute.setRouteState(4);

            List<String> invalidAddressesList = new ArrayList<>();

            for (FormattedAddress invalidAddress : invalidAddresses){
                invalidAddressesList.add(invalidAddress.getRawAddress());
                System.out.println("invalid address: " + invalidAddress.getRawAddress());
            }

            userRoute.setInvalidAddressList(invalidAddressesList);

            System.out.println("routeState: " + userRoute.getRouteState());
        }

        userRoute.setRouteState(7);
        System.out.println("routeState: " + userRoute.getRouteState());
//        organizedRoute();
    }

    private void organizedRoute(){
        userRoute.setRouteState(5);
        System.out.println("organizedRoute");
        System.out.println("routeState: " + userRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(userRoute);

//        System.out.println("Organized userRoute size: " + organizedRouteList.size());

//        fix this to check for a null object instead of the size
        if(organizedRouteList.size()>0){
            userRoute.setRouteState(7);
            System.out.println("routeState: " + userRoute.getRouteState());
        }else{
            userRoute.setRouteState(6);
            System.out.println("routeState: " + userRoute.getRouteState());
        }

    }
}

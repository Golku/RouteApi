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
     * routeState 3 = Validating the addresses
     * routeState 4 = Route has invalid addresses
     * routeState 5 = Route is being organized
     * routeState 6 = Route can not be organized
     * routeState 7 = Route is ready
     * */

    private GoogleMapsApi googleMapsApi;
    private RouteManager routeManager;
    private AddressesInformationManager addressesInformationManager;
    private RoutesOrganizer routesOrganizer;

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

        googleMapsApi = new GoogleMapsApi(databaseService);
        routesOrganizer = new RoutesOrganizer(googleMapsApi);
        addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService);
    }

    public SingleDrive getDriveInformation(SingleDriveRequest request){
        return googleMapsApi.getDriveInformation(request.getOrigin(), request.getDestination());
    }

    public void createRoute(IncomingRoute route){

        if(route.getRouteCode().isEmpty() || route.getUsername().isEmpty()){
            System.out.println("No userRoute identifier provided");
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
        }else{
//            unorganizedRoute.getWrongAddressesList().clear();
            sortAddresses();
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
        }else{
            sortAddresses();
        }
    }

    private void sortAddresses(){
        System.out.println("sortAddresses");
        System.out.println("routeState: " + unorganizedRoute.getRouteState());

        List<FormattedAddress> validAddressesList = unorganizedRoute.getAddressList();
//        This two list can be empty, find a way to FIX THIS!!
        List<FormattedAddress> businessAddresses = addressesInformationManager.findBusinessAddresses(validAddressesList);
        List<FormattedAddress> privateAddresses = addressesInformationManager.findPrivateAddresses(validAddressesList);
        unorganizedRoute.setBusinessAddressList(businessAddresses);
        unorganizedRoute.setPrivateAddressList(privateAddresses);

        for(FormattedAddress businessAddress : unorganizedRoute.getBusinessAddressList()){
            System.out.println("business address: " + businessAddress.getFormattedAddress());
        }

        for(FormattedAddress privateAddress : unorganizedRoute.getPrivateAddressList()){
            System.out.println("private address: " + privateAddress.getFormattedAddress());
        }

//        Don't call this function if privateAddresses and businessAddresses list are empty
//        organizedRoute();
        unorganizedRoute.setRouteState(7);
        System.out.println("routeState: " + unorganizedRoute.getRouteState());
//        organizedRoute();
    }

    private void organizedRoute(){
        unorganizedRoute.setRouteState(5);
        System.out.println("organizedRoute");
        System.out.println("routeState: " + unorganizedRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(unorganizedRoute);

//        System.out.println("Organized userRoute size: " + organizedRouteList.size());

//        fix this to check for a null object instead of the size
        if(organizedRouteList.size()>0){
            routeManager.createOrganizedRoute(unorganizedRoute.getRouteCode(), organizedRouteList);
            unorganizedRoute.setRouteState(7);
            System.out.println("routeState: " + unorganizedRoute.getRouteState());
        }else{
            unorganizedRoute.setRouteState(6);
            System.out.println("routeState: " + unorganizedRoute.getRouteState());
        }

    }
}

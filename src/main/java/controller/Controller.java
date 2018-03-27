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

public class Controller {

    /*
     * routeState 0 = Route does not exist
     * routeState 1 = Route was submitted with no addresses
     * routeState 2 = In queue to be process
     * routeState 3 = Validating the addresses
     * routeState 4 = Route has invalid addresses
     * routeState 5 = Route ready to be build
     * routeState 6 = Route is being organized
     * routeState 7 = Route can not be organized
     * routeState 8 = Route is ready
     * */

    private GoogleMapsApi googleMapsApi;
    private RoutesManager routesManager;
    private RoutesOrganizer routesOrganizer;
    private AddressesInformationManager addressesInformationManager;

    private UnOrganizedRoute unOrganizedRoute;

    public Controller() {

        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)//192.168.0.16
                .baseUrl("http://192.168.0.16/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = retrofitBuilder.build();
        DatabaseService databaseService = retrofit.create(DatabaseService.class);

        AddressFormatter addressFormatter = new AddressFormatter();
        this.googleMapsApi = new GoogleMapsApi(addressFormatter, databaseService);
        this.routesManager = new RoutesManager();
        this.routesOrganizer = new RoutesOrganizer(googleMapsApi);
        this.addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService);

    }

    public SingleDrive getDriveInformation(TravelInformationRequest request){
        return googleMapsApi.getDriveInformation(request.getOrigin(), request.getDestination());
    }

    public void createRoute(UnOrganizedRoute route){

        if(route.getRouteCode().isEmpty()){
            System.out.println("No route identifier provided");
            return;
        }

        routesManager.createUnorganizedRoute(route);
        this.unOrganizedRoute = routesManager.getUnorganizedRoute(route.getRouteCode());

        if(route.getAddressList().size() <= 0){
            System.out.println("Route address list is empty");
            unOrganizedRoute.setRouteState(1);
            System.out.println("routeState: " + unOrganizedRoute.getRouteState());
            return;
        }

        System.out.println("routeCode: " + unOrganizedRoute.getRouteCode());
        addressValidation(1, route.getAddressList());
    }

    public void correctedAddresses(CorrectedAddresses correctedAddresses){

        if(correctedAddresses.getRouteCode().isEmpty()){
            System.out.println("No route identifier provided");
            return;
        }

        this.unOrganizedRoute = routesManager.getUnorganizedRoute(correctedAddresses.getRouteCode());

        if(correctedAddresses.getCorrectedAddressesList().size()>0) {
            addressValidation(2, correctedAddresses.getCorrectedAddressesList());
        }else{
//            unOrganizedRoute.getWrongAddressesList().clear();
            sortAddresses();
        }
    }

    private void addressValidation(int action, ArrayList<String> addressList){

        unOrganizedRoute.setRouteState(3);

        System.out.println("addressValidation");

        System.out.println("routeState: " + unOrganizedRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

//        The formattedAddress list can be returned empty. Find a way to FIX THIS!!!
        Map<String, ArrayList<FormattedAddress>> validatedAddressMap = addressesInformationManager.validateAddressList(addressList);

        if(action == 1){
            unOrganizedRoute.setValidAddressesList(validatedAddressMap.get("validAddresses"));
        }else if(action == 2){
            List<FormattedAddress> currentValidatedAddressList = validatedAddressMap.get("validAddresses");
            List<FormattedAddress> routeValidatedAddressList = unOrganizedRoute.getValidAddressesList();
            routeValidatedAddressList.addAll(currentValidatedAddressList);
        }

        unOrganizedRoute.setInvalidAddressesList(validatedAddressMap.get("invalidAddresses"));

        for(FormattedAddress validAddress : unOrganizedRoute.getValidAddressesList()){
            System.out.println("valid address: " + validAddress.getFormattedAddress());
        }

        for (FormattedAddress invalidAddress : unOrganizedRoute.getInvalidAddressesList()){
            System.out.println("invalid address: " + invalidAddress.getRawAddress());
        }

        if(unOrganizedRoute.getInvalidAddressesList().size()>0){
            unOrganizedRoute.setRouteState(4);
            System.out.println("routeState: " + unOrganizedRoute.getRouteState());
        }else{
            sortAddresses();
        }
    }

    private void sortAddresses(){
        System.out.println("sortAddresses");
        System.out.println("routeState: " + unOrganizedRoute.getRouteState());

        List<FormattedAddress> validAddressesList = unOrganizedRoute.getValidAddressesList();
//        This two list can be empty, find a way to FIX THIS!!
        List<FormattedAddress> businessAddresses = addressesInformationManager.findBusinessAddresses(validAddressesList);
        List<FormattedAddress> privateAddresses = addressesInformationManager.findPrivateAddresses(validAddressesList);
        unOrganizedRoute.setBusinessAddressList(businessAddresses);
        unOrganizedRoute.setPrivateAddressList(privateAddresses);

        for(FormattedAddress businessAddress : unOrganizedRoute.getBusinessAddressList()){
            System.out.println("business address: " + businessAddress.getFormattedAddress());
        }

        for(FormattedAddress privateAddress : unOrganizedRoute.getPrivateAddressList()){
            System.out.println("private address: " + privateAddress.getFormattedAddress());
        }

//        Don't call this function if privateAddresses and businessAddresses list are empty
//        organizedRoute();
        unOrganizedRoute.setRouteState(5);
        System.out.println("routeState: " + unOrganizedRoute.getRouteState());
    }

    private void organizedRoute(){
        unOrganizedRoute.setRouteState(6);
        System.out.println("organizedRoute");
        System.out.println("routeState: " + unOrganizedRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(unOrganizedRoute);

//        System.out.println("Organized route size: " + organizedRouteList.size());

//        fix this to check for a null object instead of the size
        if(organizedRouteList.size()>0){
            routesManager.createOrganizedRoute(unOrganizedRoute.getRouteCode(), organizedRouteList);
            unOrganizedRoute.setRouteState(8);
            System.out.println("routeState: " + unOrganizedRoute.getRouteState());
        }else{
            unOrganizedRoute.setRouteState(7);
            System.out.println("routeState: " + unOrganizedRoute.getRouteState());
        }

    }
}

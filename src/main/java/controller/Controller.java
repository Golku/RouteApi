package controller;

import com.google.gson.Gson;
import model.*;
import model.pojos.CorrectedAddresses;
import model.pojos.FormattedAddress;
import model.pojos.SingleDrive;
import model.pojos.UnOrganizedRoute;
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
     * routeState 2 = Validating the addresses
     * routeState 3 = Route is being organized
     * routeState 4 = Route has invalid addresses
     * routeState 5 = Route can not be organized
     * routeState 6 = Route is ready
     * */

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
        GoogleMapsApi googleMapsApi = new GoogleMapsApi(addressFormatter, databaseService);
        this.routesManager = new RoutesManager();
        this.routesOrganizer = new RoutesOrganizer(googleMapsApi);
        this.addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService);

    }

    public void createRoute(UnOrganizedRoute route){
        routesManager.createUnorganizedRoute(route);
        this.unOrganizedRoute = routesManager.getUnorganizedRoute(route.getRouteCode());

        if(route.getAddressList().size() <= 0){
            System.out.println("Route list is empty");
            unOrganizedRoute.setRouteState(1);
            System.out.println("routeState: " + unOrganizedRoute.getRouteState());
            return;
        }

        System.out.println("routeCode: " + unOrganizedRoute.getRouteCode());
        addressValidation(1, route.getAddressList());
    }

    public void correctedAddresses(CorrectedAddresses correctedAddresses){
        if(correctedAddresses.getCorrectedAddressesList().size()>0) {
            this.unOrganizedRoute = routesManager.getUnorganizedRoute(correctedAddresses.getRouteCode());
            addressValidation(2, correctedAddresses.getCorrectedAddressesList());
        }else{
//            unOrganizedRoute.getWrongAddressesList().clear();
            sortAddresses();
        }
    }

    private void addressValidation(int action, ArrayList<String> addressList){

        unOrganizedRoute.setRouteState(2);

        System.out.println("addressValidation");

        System.out.println("routeState: " + unOrganizedRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

        Map<String, List<FormattedAddress>> validatedAddressLists = addressesInformationManager.validateAddressList(addressList);

        if(action == 1){
            unOrganizedRoute.setAllValidAddressesList(validatedAddressLists.get("validAddresses"));
        }else if(action == 2){
            List<FormattedAddress> currentValidatedAddressList = validatedAddressLists.get("validAddresses");
            List<FormattedAddress> routeValidatedAddressList = unOrganizedRoute.getAllValidAddressesList();
            routeValidatedAddressList.addAll(currentValidatedAddressList);
        }

        unOrganizedRoute.setInvalidAddressesList(validatedAddressLists.get("invalidAddresses"));

        for(FormattedAddress validAddress : unOrganizedRoute.getAllValidAddressesList()){
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
        List<FormattedAddress> validAddressesList = unOrganizedRoute.getAllValidAddressesList();
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

        organizedRoute();
    }

    private void organizedRoute(){
        unOrganizedRoute.setRouteState(3);
        System.out.println("organizedRoute");
        System.out.println("routeState: " + unOrganizedRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

        List<SingleDrive> organizedRouteList = routesOrganizer.organizeRouteClosestAddress(unOrganizedRoute);

        //fix this to check for a null object instead of the size
        if(organizedRouteList.size()>0){
            routesManager.createOrganizedRoute(unOrganizedRoute.getRouteCode(), organizedRouteList);
            unOrganizedRoute.setRouteState(6);
            System.out.println("routeState: " + unOrganizedRoute.getRouteState());
        }else{
            unOrganizedRoute.setRouteState(5);
            System.out.println("routeState: " + unOrganizedRoute.getRouteState());
        }

    }
}

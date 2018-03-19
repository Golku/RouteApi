package controller;

import com.google.gson.Gson;
import model.*;
import model.pojos.CorrectedAddresses;
import model.pojos.FormattedAddress;
import model.pojos.UnOrganizedRoute;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {

    /*
     * routeState 1 = validatingAddresses
     * routeState 2 = organizingRoute
     * routeState 3 = routeOrganized
     * routeState 4 = hasInvalidAddresses
     * routeState 5 = invalidRoute
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
                .baseUrl("http://10.163.48.151/map/v1/")
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

        System.out.println("addressValidation");

        unOrganizedRoute.setRouteState(1);

        Map<String, List<FormattedAddress>> validatedAddressLists = addressesInformationManager.validateAddressList(addressList);

        if(action == 1){
            unOrganizedRoute.setAllValidatedAddressesList(validatedAddressLists.get("validAddresses"));
        }else if(action == 2){
            List<FormattedAddress> currentValidatedAddressList = validatedAddressLists.get("validAddresses");
            List<FormattedAddress> routeValidatedAddressList = unOrganizedRoute.getAllValidatedAddressesList();
            routeValidatedAddressList.addAll(currentValidatedAddressList);
        }

        unOrganizedRoute.setWrongAddressesList(validatedAddressLists.get("wrongAddresses"));

        if(unOrganizedRoute.getWrongAddressesList().size()>0){
            unOrganizedRoute.setRouteState(4);

            for (FormattedAddress address : unOrganizedRoute.getWrongAddressesList()){
                System.out.println("wrong address: " + address.getRawAddress());
            }

        }else{

            for(FormattedAddress address : unOrganizedRoute.getAllValidatedAddressesList()){
                System.out.println("validated address: " + address.getFormattedAddress());
            }

            sortAddresses();
        }
    }

    private void sortAddresses(){
        System.out.println("sortAddresses");
        List<FormattedAddress> validatedAddressList = unOrganizedRoute.getAllValidatedAddressesList();
        List<FormattedAddress> businessAddresses = addressesInformationManager.findBusinessAddresses(validatedAddressList);
        List<FormattedAddress> privateAddresses = addressesInformationManager.findPrivateAddresses(validatedAddressList);
        unOrganizedRoute.setBusinessAddressList(businessAddresses);
        unOrganizedRoute.setPrivateAddressList(privateAddresses);

        for(FormattedAddress address : unOrganizedRoute.getPrivateAddressList()){
            System.out.println("Private address: " + address.getFormattedAddress());
        }

        for(FormattedAddress address : unOrganizedRoute.getBusinessAddressList()){
            System.out.println("business address: " + address.getFormattedAddress());
        }

        organizedRoute();
    }

    private void organizedRoute(){
        System.out.println("organizedRoute");
        unOrganizedRoute.setRouteState(2);
    }
}

package controller;

import com.google.gson.Gson;
import model.*;
import model.pojos.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;
import java.util.Map;

public class Controller {

    /*
     * routeState 0 = Route does not exist
     * routeState 1 = Route was submitted with no addresses
     * routeState 2 = In queue to be process
     * routeState 3 = Validating the addresses
     * routeState 4 = Route has invalid addresses
     * routeState 5 = Route is being organized
     * routeState 6 = Route can not be organized
     * routeState 7 = Route is ready
     * */

    private GoogleMapsApi googleMapsApi;
    private ContainerManager containerManager;
    private RouteManager routeManager;
    private AddressesInformationManager addressesInformationManager;
    private RoutesOrganizer routesOrganizer;

    private UnorganizedRoute unorganizedRoute;

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
        this.containerManager = new ContainerManager();
        this.routesOrganizer = new RoutesOrganizer(googleMapsApi);
        this.addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService);

    }

    public SingleDrive getDriveInformation(SingleDriveRequest request){
        return googleMapsApi.getDriveInformation(request.getOrigin(), request.getDestination());
    }

    public void createRoute(IncomingRoute route){

        if(route.getRouteCode().isEmpty() || route.getUsername().isEmpty()){
            System.out.println("No route identifier provided");
            return;
        }

        routeManager.createRoute(route.getRouteCode());
        this.unorganizedRoute = routeManager.getUnorganizedRoute(route.getRouteCode());

        if(route.getAddressList().size() <= 0){
            System.out.println("Route address list is empty");
            unorganizedRoute.setRouteState(1);
            System.out.println("routeState: " + unorganizedRoute.getRouteState());
            return;
        }

        System.out.println("routeCode: " + unorganizedRoute.getRouteCode());
        addressValidation(1, route.getAddressList());
    }

    public void correctedAddresses(CorrectedAddresses correctedAddresses){

        if(correctedAddresses.getRouteCode().isEmpty()){
            System.out.println("No route identifier provided");
            return;
        }

        this.unorganizedRoute = routeManager.getUnorganizedRoute(correctedAddresses.getRouteCode());

        if(correctedAddresses.getCorrectedAddressesList().size()>0) {
            addressValidation(2, correctedAddresses.getCorrectedAddressesList());
        }else{
//            unorganizedRoute.getWrongAddressesList().clear();
            sortAddresses();
        }
    }

    private void addressValidation(int action, List<String> addressList){

        unorganizedRoute.setRouteState(3);

        System.out.println("addressValidation");

        System.out.println("routeState: " + unorganizedRoute.getRouteState());

//        try{
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//
//        }

//        The formattedAddress list can be returned empty. Find a way to FIX THIS!!!
        Map<String, List<FormattedAddress>> validatedAddressMap = addressesInformationManager.validateAddressList(addressList);

        if(action == 1){
            unorganizedRoute.setAddressList(validatedAddressMap.get("validAddresses"));
        }else if(action == 2){
            List<FormattedAddress> currentValidatedAddressList = validatedAddressMap.get("validAddresses");
            List<FormattedAddress> routeValidatedAddressList = unorganizedRoute.getAddressList();
            routeValidatedAddressList.addAll(currentValidatedAddressList);
        }

        unorganizedRoute.setInvalidAddressesList(validatedAddressMap.get("invalidAddresses"));

        for(FormattedAddress validAddress : unorganizedRoute.getAddressList()){
            System.out.println("valid address: " + validAddress.getFormattedAddress());
        }

        for (FormattedAddress invalidAddress : unorganizedRoute.getInvalidAddressesList()){
            System.out.println("invalid address: " + invalidAddress.getRawAddress());
        }

        if(unorganizedRoute.getInvalidAddressesList().size()>0){
            unorganizedRoute.setRouteState(4);
            System.out.println("routeState: " + unorganizedRoute.getRouteState());
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

//        System.out.println("Organized route size: " + organizedRouteList.size());

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

    public Container fetchContainer(String username) {
        Container container = containerManager.getContainer(username);

        if (container == null){
            container = containerManager.createContainer(username);
            Route route = routeManager.getRoute(username);
            if(route != null){
                container.setRoute(route);
            }
        }

        return container;
    }

    private Route fetchRoute(String username){
        return routeManager.getRoute(username);
    }
}

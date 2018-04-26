package controller;

import com.google.gson.Gson;
import model.*;
import model.pojos.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class RouteController {

    /*
     * routeState 0 = Route does not exist
     * routeState 1 = Route was submitted with no addresses
     * routeState 2 = Validating the addresses
     * routeState 3 = Route is being organized
     * routeState 4 = Route can not be organized
     * routeState 5 = Route is ready
     * */

    private ContainerController containerController;
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

        AddressFormatter addressFormatter = new AddressFormatter();
        GoogleMapsApi googleMapsApi = new GoogleMapsApi(databaseService, addressFormatter);
        containerController = new ContainerController();
        routeManager = new RouteManager();
        routesOrganizer = new RoutesOrganizer(googleMapsApi);
        addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService, addressFormatter);
    }

    public Route fetchRoute(String username) {
        Route route = routeManager.getRoute(username);

        if (route == null) {
            route = new Route();
            route.setRouteState(0);
        }

        return route;
    }

    public void createRoute(IncomingRoute route) {

        //temporary
        route.setRouteCode("1");
        //log this list
        //route.getAddressList();

        if (route.getRouteCode().isEmpty() || route.getUsername().isEmpty()) {
            System.out.println("No userRoute identifier provided");
            //log occurrences of this case
            return;
        }

        System.out.println("Route username: " + userRoute.getUsername());
        System.out.println("routeCode: " + userRoute.getRouteCode());

        routeManager.createRoute(route.getUsername(), route.getRouteCode());
        userRoute = routeManager.getRoute(route.getUsername());
        containerController.putRouteInContainer(route.getUsername(), userRoute);

        if (route.getAddressList().size() <= 0) {
            System.out.println("Route address list is empty");
            userRoute.setRouteState(1);
            System.out.println("routeState: " + userRoute.getRouteState());
            return;
        }

        userRoute.setRouteState(2);

        System.out.println("addressValidation");
        System.out.println("routeState: " + userRoute.getRouteState());

        for (String address : route.getAddressList()) {
            validateAddress(address);
        }

        userRoute.setRouteState(5);
        System.out.println("routeState: " + userRoute.getRouteState());
    }

    public void correctedAddresses(CorrectedAddresses correctedAddresses) {

        if (correctedAddresses.getUsername().isEmpty()) {
            System.out.println("No userRoute identifier provided");
            return;
        }

        userRoute = routeManager.getRoute(correctedAddresses.getUsername());

        if (correctedAddresses.getCorrectedAddressesList().size() > 0) {
//            addressValidation(2, correctedAddresses.getCorrectedAddressesList());
        }
    }

    private void validateAddress(String address) {

        FormattedAddress verifiedAddress = addressesInformationManager.validateAddress(address);

        if(!verifiedAddress.isInvalid()){
            addressesInformationManager.setAddressType(verifiedAddress);
        }

        userRoute.getAddressList().add(verifiedAddress);
    }

    private void organizedRoute() {
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
        if (organizedRouteList.size() > 0) {
            userRoute.setRouteState(7);
            System.out.println("routeState: " + userRoute.getRouteState());
        } else {
            userRoute.setRouteState(6);
            System.out.println("routeState: " + userRoute.getRouteState());
        }

    }
}

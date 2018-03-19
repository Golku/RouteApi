package controller;

import model.*;
import model.pojos.ApiResponse;
import model.pojos.UnOrganizedRoute;

import java.util.ArrayList;

public class GetRouteController {

    private RoutesManager routesManager;

    public GetRouteController() {
        this.routesManager = new RoutesManager();
    }

    public ApiResponse checkForRouteState(String routeCode) {

        ApiResponse apiResponse = new ApiResponse();

        if (!routeCode.isEmpty()) {

            UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(routeCode);

            if (unOrganizedRoute != null) {

                int routeState = unOrganizedRoute.getRouteState();

                if(routeState == 3) {

                    apiResponse.setRouteState(routeState);
                    apiResponse.setOrganizedRoute(routesManager.getOrganizedRoute(routeCode));

                }else if(routeState == 4){

                    ArrayList<String> invalidAddresses = new ArrayList<>();

                    for (int i = 0; i < unOrganizedRoute.getWrongAddressesList().size(); i++) {
                        invalidAddresses.add(unOrganizedRoute.getWrongAddressesList().get(i).getRawAddress());
                    }

                    apiResponse.setRouteState(routeState);
                    apiResponse.setInvalidAddresses(invalidAddresses);

                }else{
                    apiResponse.setRouteState(routeState);
                }

            } else {
                apiResponse.setRouteState(5);
            }
        } else {
            apiResponse.setRouteState(5);
        }

        apiResponse.setRequestType("get route");

        return apiResponse;
    }

}

package controller;

import model.*;
import model.pojos.ApiResponse;
import model.pojos.FormattedAddress;
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

                if(routeState == 8) {

                    apiResponse.setRouteState(routeState);
                    apiResponse.setOrganizedRoute(routesManager.getOrganizedRoute(routeCode));

                }else if(routeState == 4){

                    apiResponse.setRouteState(routeState);

                    ArrayList<String> invalidAddresses = new ArrayList<>();

                    for (FormattedAddress invalidAddress : unOrganizedRoute.getInvalidAddressesList()) {
                        invalidAddresses.add(invalidAddress.getRawAddress());
                    }

                    apiResponse.setInvalidAddresses(invalidAddresses);

                }else if (routeState == 5){
                    apiResponse.setRouteState(routeState);
                    apiResponse.setValidAddresses(unOrganizedRoute.getValidAddressesList());
                }else{
                    apiResponse.setRouteState(routeState);
                }

            } else {
                apiResponse.setRouteState(0);
            }
        } else {
            apiResponse.setRouteState(0);
        }

        apiResponse.setRequestType("get route");

        return apiResponse;
    }

}

package controller;

import model.*;
import model.pojos.ApiResponse;
import model.pojos.UnOrganizedRoute;

import java.util.ArrayList;

public class GetRouteController {

    private RoutesManager routesManager;

    public GetRouteController () {
        this.routesManager = new RoutesManager();
    }

    public ApiResponse getRoute (String routeCode){

        ApiResponse apiResponse = new ApiResponse();

        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(routeCode);

        if(unOrganizedRoute != null) {

            apiResponse.setRouteIsNull(false);

            if (unOrganizedRoute.isOrganizingInProgress()) {
                apiResponse.setOrganizingInProgress(true);
            } else {
                apiResponse.setOrganizingInProgress(false);
                apiResponse.setOrganizedRoute(routesManager.getOrganizedRoute(routeCode));
            }
        }else{
            apiResponse.setRouteIsNull(true);
        }
        return apiResponse;
    }

}

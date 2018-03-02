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

            if (unOrganizedRoute.isOrganizingInProgress()) {
                apiResponse.setOrganizingInProgress(true);
            } else {

                apiResponse.setOrganizingInProgress(false);

                if (unOrganizedRoute.getWrongAddressesList().size() > 0) {

                    apiResponse.setRouteHasInvalidAddresses(true);

                    ArrayList<String> invalidAddresses = new ArrayList<>();

                    for(int i=0; i<unOrganizedRoute.getWrongAddressesList().size(); i++){
                        invalidAddresses.add(unOrganizedRoute.getWrongAddressesList().get(i).getRawAddress());
                    }

                    apiResponse.setInvalidAddresses(invalidAddresses);

                } else {
                    apiResponse.setOrganizedRoute(routesManager.getOrganizedRoute(routeCode));
                }
            }
        }else{
            apiResponse.setRouteIsNull(true);
        }
        return apiResponse;
    }

}

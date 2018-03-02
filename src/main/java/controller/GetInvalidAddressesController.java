package controller;

import model.*;
import model.pojos.ApiResponse;
import model.pojos.UnOrganizedRoute;

import java.util.ArrayList;

public class GetInvalidAddressesController {

    private RoutesManager routesManager;

    public GetInvalidAddressesController() {
        this.routesManager = new RoutesManager();
    }

    public ApiResponse checkForInvalidAddresses(String routeCode){

        ApiResponse apiResponse = new ApiResponse();

        if(!routeCode.isEmpty()) {

            UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(routeCode);

            if (unOrganizedRoute != null) {

                apiResponse.setRouteIsNull(false);

                if (!unOrganizedRoute.getAddressValidatingInProgress()) {

                    apiResponse.setOrganizingInProgress(false);

                    if (unOrganizedRoute.getHasInvalidAddresses()) {

                        apiResponse.setRouteHasInvalidAddresses(true);

                        ArrayList<String> invalidAddresses = new ArrayList<>();

                        for (int i = 0; i < unOrganizedRoute.getWrongAddressesList().size(); i++) {
                            invalidAddresses.add(unOrganizedRoute.getWrongAddressesList().get(i).getRawAddress());
                        }

                        apiResponse.setInvalidAddresses(invalidAddresses);

                    } else {
                        apiResponse.setRouteHasInvalidAddresses(false);
                    }

                }else{
                    apiResponse.setOrganizingInProgress(true);
                }

            }else{
                apiResponse.setRouteIsNull(true);
            }
        }else{
            apiResponse.setRouteIsNull(true);
        }

        return apiResponse;
    }

}

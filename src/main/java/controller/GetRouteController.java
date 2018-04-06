package controller;

import model.RoutesManager;
import model.pojos.RouteResponse;
import model.pojos.FormattedAddress;
import model.pojos.UnOrganizedRoute;

import java.util.ArrayList;
import java.util.List;

public class GetRouteController {

    private RoutesManager routesManager;

    public GetRouteController() {
        this.routesManager = new RoutesManager();
    }

    public RouteResponse checkForRouteState(String routeCode) {

        RouteResponse routeResponse = new RouteResponse();

        if (!routeCode.isEmpty()) {

            UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(routeCode);

            if (unOrganizedRoute != null) {

                int routeState = unOrganizedRoute.getRouteState();

                if(routeState == 8) {

                    routeResponse.setRouteState(routeState);
                    routeResponse.setOrganizedRoute(routesManager.getOrganizedRoute(routeCode));

                }else if(routeState == 4){

                    routeResponse.setRouteState(routeState);

                    List<String> invalidAddresses = new ArrayList<>();

                    for (FormattedAddress invalidAddress : unOrganizedRoute.getInvalidAddressesList()) {
                        invalidAddresses.add(invalidAddress.getRawAddress());
                    }

                    routeResponse.setInvalidAddresses(invalidAddresses);

                }else if (routeState == 5){
                    routeResponse.setRouteState(routeState);
                    routeResponse.setUnOrganizedRoute(unOrganizedRoute);
                }else{
                    routeResponse.setRouteState(routeState);
                }

            } else {
                routeResponse.setRouteState(0);
            }
        } else {
            routeResponse.setRouteState(0);
        }

        return routeResponse;
    }

}

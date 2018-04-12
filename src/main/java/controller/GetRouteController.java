package controller;

import model.RoutesManager;
import model.pojos.Route;
import model.pojos.RouteResponse;
import model.pojos.FormattedAddress;
import model.pojos.UnorganizedRoute;

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

            UnorganizedRoute unorganizedRoute = routesManager.getUnorganizedRoute(routeCode);

            if (unorganizedRoute != null) {

                int routeState = unorganizedRoute.getRouteState();

                if(routeState == 7) {

                    Route route = new Route();

                    route.setRouteCode(unorganizedRoute.getRouteCode());
                    route.setAddressList(unorganizedRoute.getAddressList());
                    route.setPrivateAddressList(unorganizedRoute.getPrivateAddressList());
                    route.setBusinessAddressList(unorganizedRoute.getBusinessAddressList());

                    if(routesManager.getOrganizedRoute(routeCode) != null) {
                        route.setRouteList(routesManager.getOrganizedRoute(routeCode).getRouteList());
                    }

                    routeResponse.setRouteState(routeState);
                    routeResponse.setRoute(route);

                }else if(routeState == 4){

                    routeResponse.setRouteState(routeState);

                    List<String> invalidAddresses = new ArrayList<>();

                    for (FormattedAddress invalidAddress : unorganizedRoute.getInvalidAddressesList()) {
                        invalidAddresses.add(invalidAddress.getRawAddress());
                    }

                    routeResponse.setInvalidAddresses(invalidAddresses);

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

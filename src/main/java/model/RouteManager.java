package model;

import model.pojos.FormattedAddress;
import model.pojos.Route;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteManager {

    private static Map<String, Route> routes = new HashMap<>();

    public void createRoute(String username, String routeCode){
        Route route = new Route();
        route.setUsername(username);
        route.setRouteCode(routeCode);
        List<FormattedAddress> addressList = new ArrayList<>();
        route.setAddressList(addressList);
        routes.put(username, route);
    }

    public Route getRoute(String username){
        return routes.get(username);
    }
}

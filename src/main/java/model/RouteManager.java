package model;

import model.pojos.Route;
import java.util.HashMap;
import java.util.Map;

public class RouteManager {

    private static Map<String, Route> routes = new HashMap<>();

    public void createRoute(String username, String routeCode){
        Route route = new Route();
        route.setUsername(username);
        route.setRouteCode(routeCode);
        routes.put(username, route);
    }

    public Route getRoute(String username){
        return routes.get(username);
    }
}

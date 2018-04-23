package model;

import model.pojos.OrganizedRoute;
import model.pojos.Route;
import model.pojos.SingleDrive;
import model.pojos.UnorganizedRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteManager {

    private static Map<String, Route> routes = new HashMap<>();
    private static Map<String, UnorganizedRoute> unorganizedRoutes = new HashMap<>();
    private static Map<String, OrganizedRoute> organizedRoutes = new HashMap<>();

    public void createRoute(String username){
        Route route = new Route();
        routes.put(username, route);
    }

    public void createUnorganizedRoute(String routeCode){
        UnorganizedRoute unorganizedRoute = new UnorganizedRoute(routeCode);
        unorganizedRoutes.put(routeCode, unorganizedRoute);
    }

    public void createOrganizedRoute(String routeCode, List<SingleDrive> organizedRouteList){

        UnorganizedRoute unorganizedRoute = getUnorganizedRoute(routeCode);

        OrganizedRoute organizedRoute = new OrganizedRoute(
                routeCode,
                unorganizedRoute.getAddressList(),
                unorganizedRoute.getPrivateAddressList(),
                unorganizedRoute.getBusinessAddressList(),
                organizedRouteList
        );

        organizedRoutes.put(routeCode, organizedRoute);
    }

    public Route getRoute(String username){
        Route route;
        if(routes.get(username) != null){
            route = routes.get(username);
            System.out.println("route is not null");
        }else{
            route = null;
            System.out.println("route is null");
        }

        return route;
    }

    public UnorganizedRoute getUnorganizedRoute(String routeCode){
        return unorganizedRoutes.get(routeCode);
    }

    public OrganizedRoute getOrganizedRoute(String routeCode){
        return organizedRoutes.get(routeCode);
    }
}

package model;

import model.pojos.OrganizedRoute;
import model.pojos.SingleDrive;
import model.pojos.UnorganizedRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesManager {

    //Change this two to vectors to make it usable with more threads
    private static Map<String, UnorganizedRoute> unorganizedRoutes = new HashMap<>();
    private static Map<String, OrganizedRoute> organizedRoutes = new HashMap<>();

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

    public UnorganizedRoute getUnorganizedRoute(String routeCode){
        return unorganizedRoutes.get(routeCode);
    }

    public OrganizedRoute getOrganizedRoute(String routeCode){
        return organizedRoutes.get(routeCode);
    }

}
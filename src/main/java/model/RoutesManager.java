package model;

import model.pojos.OrganizedRoute;
import model.pojos.SingleDrive;
import model.pojos.UnOrganizedRoute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesManager {

    //Change this two to vectors to make it usable with more threads
    private static Map<String, UnOrganizedRoute> unorganizedRoutes = new HashMap<>();
    private static Map<String, OrganizedRoute> organizedRoutes = new HashMap<>();

    public void createUnorganizedRoute(UnOrganizedRoute unOrganizedRoute){
        String routeCode = unOrganizedRoute.getRouteCode();
        unorganizedRoutes.put(routeCode, unOrganizedRoute);
    }

    public void createOrganizedRoute(String routeCode, List<SingleDrive> organizedRouteList){

        int privateAddressesCount = getUnorganizedRoute(routeCode).getPrivateAddressList().size();
        int businessAddressesCount = getUnorganizedRoute(routeCode).getBusinessAddressList().size();
        int invalidAddressesCount = getUnorganizedRoute(routeCode).getInvalidAddressesList().size();

        OrganizedRoute organizedRoute = new OrganizedRoute(
                routeCode,
                privateAddressesCount,
                businessAddressesCount,
                invalidAddressesCount,
                organizedRouteList
        );

        organizedRoutes.put(routeCode, organizedRoute);
    }

    public UnOrganizedRoute getUnorganizedRoute(String routeCode){
        return unorganizedRoutes.get(routeCode);
    }

    public OrganizedRoute getOrganizedRoute(String routeCode){
        return organizedRoutes.get(routeCode);
    }

}
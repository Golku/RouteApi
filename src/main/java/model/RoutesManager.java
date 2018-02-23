package model;

import model.pojos.FormattedAddress;
import model.pojos.OrganizedRoute;
import model.pojos.SingleDrive;
import model.pojos.UnOrganizedRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesManager {

    private List<String> routeCodeList = new ArrayList<>();
    private static Map<String, UnOrganizedRoute> unorganizedRoutes = new HashMap<>();
    private static Map<String, OrganizedRoute> organizedRoutes = new HashMap<>();

    public void createUnorganizedRoute(String routeCode, Map<String, List<FormattedAddress>> validatedAddressLists){

        UnOrganizedRoute UnOrganizedRoute = new UnOrganizedRoute(
                routeCode,
                validatedAddressLists.get("validAddresses"),
                validatedAddressLists.get("privateAddresses"),
                validatedAddressLists.get("businessAddresses"),
                validatedAddressLists.get("wrongAddresses")
        );

        routeCodeList.add(routeCode);

//        System.out.println(UnOrganizedRoute.getAllValidatedAddressesList().size());
//        System.out.println(UnOrganizedRoute.getPrivateAddressList().size());
//        System.out.println(UnOrganizedRoute.getBusinessAddressList().size());
//        System.out.println(UnOrganizedRoute.getWrongAddressesList().size());

        unorganizedRoutes.put(routeCode, UnOrganizedRoute);

    }

    public void createOrganizedRoute(String routeCode, List<SingleDrive> organizedRouteList){

        int privateAddressesCount = getUnorganizedRoute(routeCode).getPrivateAddressList().size();
        int businessAddressesCount = getUnorganizedRoute(routeCode).getBusinessAddressList().size();
        int wrongAddressesCount = getUnorganizedRoute(routeCode).getWrongAddressesList().size();

        OrganizedRoute organizedRoute = new OrganizedRoute(
                routeCode,
                privateAddressesCount,
                businessAddressesCount,
                wrongAddressesCount,
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
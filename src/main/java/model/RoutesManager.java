package model;

import model.pojos.FormattedAddress;
import model.pojos.SingleDrive;
import model.pojos.SingleOrganizedRoute;
import model.pojos.SingleUnOrganizedRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesManager {

    private List<String> routeCodeList = new ArrayList<>();
    private static Map<String, SingleUnOrganizedRoute> UnorganizedRoutes = new HashMap<>();
    private static Map<String, SingleOrganizedRoute> organizedRoutes = new HashMap<>();

    public void createUnorganizedRoute(String routeCode, Map<String, List<FormattedAddress>> validatedAddressLists){

        SingleUnOrganizedRoute singleUnOrganizedRoute = new SingleUnOrganizedRoute(
                routeCode,
                validatedAddressLists.get("validAddresses"),
                validatedAddressLists.get("privateAddresses"),
                validatedAddressLists.get("businessAddresses"),
                validatedAddressLists.get("wrongAddresses")
        );

        routeCodeList.add(routeCode);

//        System.out.println(singleUnOrganizedRoute.getAllValidatedAddressesList().size());
//        System.out.println(singleUnOrganizedRoute.getPrivateAddressList().size());
//        System.out.println(singleUnOrganizedRoute.getBusinessAddressList().size());
//        System.out.println(singleUnOrganizedRoute.getWrongAddressesList().size());

        UnorganizedRoutes.put(routeCode, singleUnOrganizedRoute);

    }

    public void createOrganizedRoute(String routeCode, List<SingleDrive> organizedRouteList){

        int privateAddressesCount = getSingleUnorganizedRoute(routeCode).getPrivateAddressList().size();
        int businessAddressesCount = getSingleUnorganizedRoute(routeCode).getBusinessAddressList().size();
        int wrongAddressesCount = getSingleUnorganizedRoute(routeCode).getWrongAddressesList().size();

        SingleOrganizedRoute singleOrganizedRoute = new SingleOrganizedRoute(
                routeCode,
                privateAddressesCount,
                businessAddressesCount,
                wrongAddressesCount,
                organizedRouteList
        );

        organizedRoutes.put(routeCode, singleOrganizedRoute);
    }

    public static SingleUnOrganizedRoute getSingleUnorganizedRoute(String routeCode){
        return UnorganizedRoutes.get(routeCode);
    }

    public static SingleOrganizedRoute getSingleOrganizedRoute(String routeCode){
        return organizedRoutes.get(routeCode);
    }

    public Map getAllUnorganizedRoutes(){
        return UnorganizedRoutes;
    }

    public Map getAllOrganizedRoutes(){
        return organizedRoutes;
    }

}

package com.RouteApi;

import controller.MainController;
import model.RoutesManager;
import model.pojos.IncomingRoute;
import model.pojos.SingleOrganizedRoute;
import model.pojos.SingleUnOrganizedRoute;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private MainController controller = new MainController();

    public SingleOrganizedRoute getOrganizedRoute(String routeCode) {
        return RoutesManager.getSingleOrganizedRoute(routeCode);
    }

    public SingleUnOrganizedRoute getUnOrganizedRoute(String routeCode) {
        return RoutesManager.getSingleUnorganizedRoute(routeCode);
    }

    public void createRoute() {

        List<String> addressesList = new ArrayList<>();

//        addressesList.add("Delfgaauwstraat 72C, 3037 LR Rotterdam, Netherlands");
//        addressesList.add("Crooswijkseweg 55, 3034 HB Rotterdam, Netherlands");
//        addressesList.add("Van Meekerenstraat 116B, 3034 GD Rotterdam, Netherlands");
//        addressesList.add("Keersopstraat 17, 3044 EX Rotterdam, Netherlands");
//        addressesList.add("A. Noordewier-Reddingiuslaan 35, 3066 JA Rotterdam, Netherlands");
//        addressesList.add("'s-Gravenweg 305, 3062 ZG Rotterdam, Netherlands");
//        addressesList.add("Els Borst-Eilersplein 275, Den haag");
//        addressesList.add("Hoofdweg 480, rotterdam");
//        addressesList.add("blaak 100, rotterdam");
//        addressesList.add("Deimanstraat 96, Den haag");
//        addressesList.add("Van Musschenbroekstraat 45, Den haag");
//        addressesList.add("Allard Piersonlaan 294, Den haag");
//        addressesList.add("Koopmans van Boekerenstraat 65, Den haag");
//        addressesList.add("Goeverneurlaan 102, Den haag");
//        addressesList.add("Antheunisstraat 196, Den haag");
//        addressesList.add("Haverschmidtstraat 15, Den haag");
//        addressesList.add("Hoefkade 99, Den haag");
//        addressesList.add("Jan de Baenstraat 15, Den haag");
//        addressesList.add("Hobbemastraat 85, Den haag");
//        addressesList.add("Kempstraat 44, Den haag");
//        addressesList.add("Schaarsbergenstraat 180, Den haag");
//        addressesList.add("Goeverneurlaan 102, Den haag");
//        addressesList.add("Antheunisstraat 196, Den haag");
//        addressesList.add("Haverschmidtstraat 15, Den haag");
//        addressesList.add("Hoefkade 99, Den haag");
//        addressesList.add("Jan de Baenstraat 15, Den haag");
//        addressesList.add("Hobbemastraat 85, Den haag");
//        addressesList.add("Kempstraat 44, Den haag");
//        addressesList.add("Schaarsbergenstraat 180, Den haag");
//        addressesList.add("Zuiderparklaan 391, Den haag");
//        addressesList.add("Klimopstraat 109, Den haag");
//        addressesList.add("Toscaninistraat 92, Den haag");
//        addressesList.add("Denijsstraat 149, Den haag");
//        addressesList.add("Machiel Vrijenhoeklaan 450, Den haag");
//        addressesList.add("Loosduinse Hoofdstraat 602, Den haag");
//        addressesList.add("Nieuwendamlaan 184, Den haag");
//        addressesList.add("Medemblikstraat 240, Den haag");
//        addressesList.add("Reitzstraat 185, Den haag");
//        addressesList.add("Joubertplantsoen 147, Den haag");
//        addressesList.add("Van der Helststraat 48, Den haag");
//        addressesList.add("Verisstraat 36, Den haag");
//        addressesList.add("Beetsstraat 94, Den haag");
//        addressesList.add("Hasebroekstraat 84, Den haag");
//        addressesList.add("Hasebroekstraat 112, Den haag");
//        addressesList.add("Van Meursstraat 4, Den haag");
//        addressesList.add("Delfgaauwstraat 72C, 3037 LR Rotterdam, Netherlands");
//        addressesList.add("Crooswijkseweg 55, 3034 HB Rotterdam, Netherlands");
//        addressesList.add("Van Meekerenstraat 116B, 3034 GD Rotterdam, Netherlands");
//        addressesList.add("Keersopstraat 17, 3044 EX Rotterdam, Netherlands");
//        addressesList.add("A. Noordewier-Reddingiuslaan 35, 3066 JA Rotterdam, Netherlands");
//        addressesList.add("'s-Gravenweg 305, 3062 ZG Rotterdam, Netherlands");
//        addressesList.add("Hommelstraat 24, 3061 VB Rotterdam, Netherlands");
//        addressesList.add("Schultz van Hagenstraat 73, 3062 XJ Rotterdam, Netherlands");
//        addressesList.add("Mertensstraat 64, 3067 CL Rotterdam, Netherlands");
//        addressesList.add("Brindisipad 8, 3067 WN Rotterdam, Netherlands");
//        addressesList.add("Verhagenstraat 32, 3067 TH Rotterdam, Netherlands");
//        addressesList.add("Robert Kochplaats 166, 3068 JC Rotterdam, Netherlands");
//        addressesList.add("Andre Gideplaats 347, 3069 EJ Rotterdam, Netherlands");
//        addressesList.add("Philip Vingboonsstraat 58, 3067 ZC Rotterdam, Netherlands");
//        addressesList.add("Blondeelstraat 26, 3067 VA Rotterdam, Netherlands");
//        addressesList.add("Golda Meirstraat 5, 3066 VE Rotterdam, Netherlands");
//        addressesList.add("Adriaan Roland Holststraat 52, 3069 WK Rotterdam, Netherlands");
//        addressesList.add("Kralingse Kerklaan 616, 3065 CC Rotterdam, Netherlands");
//        addressesList.add("Jan Palachstraat 9, 3065 EB Rotterdam, Netherlands");
//        addressesList.add("Admiraliteitskade 60, 3063 ED Rotterdam, Netherlands");
//        addressesList.add("Essenlaan 20, 3062 NN Rotterdam, Netherlands");
//        addressesList.add("Vijverlaan 48, 3062 HL Rotterdam, Netherlands");

        controller.validateAddressList("1", addressesList);

    }

    public SingleOrganizedRoute organizeRoute(IncomingRoute route) {

        controller.validateAddressList(route.getRouteCode(), route.getAddressList());

        return controller.organizeRoute(route.getRouteCode(), route.getOrigin());

    }

}

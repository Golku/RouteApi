package controller;

import model.*;
import model.pojos.*;

public class RouteController extends BaseController{

    private ContainerManager containerManager;
    private DbManager dbManager;
    private GoogleMapsApi googleMapsApi;
    private AddressFormatter addressFormatter;

    private Container container;

    public RouteController() {
        containerManager = getContainerManager();
        googleMapsApi = getGoogleMapsApi();
        dbManager = getDbManager();
        addressFormatter = getAddressFormatter();
    }

    public void organizedRoute(OrganizeRouteRequest request) {

    }
}

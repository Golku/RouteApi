package controller;

import model.AddressFormatter;
import model.AddressesInformationManager;
import model.GoogleMapsApi;
import model.RoutesManager;
import model.pojos.CorrectedAddresses;
import model.pojos.FormattedAddress;
import model.pojos.UnOrganizedRoute;

import java.util.List;
import java.util.Map;

public class SubmitCorrectedAddressesController {

    private RoutesManager routesManager;
    private AddressesInformationManager addressesInformationManager;

    public SubmitCorrectedAddressesController () {
        AddressFormatter addressFormatter = new AddressFormatter();
        GoogleMapsApi googleMapsApi = new GoogleMapsApi(addressFormatter);
        this.routesManager = new RoutesManager();
        this.addressesInformationManager = new AddressesInformationManager(googleMapsApi);
    }

    public void checkSubmittedAddresses(CorrectedAddresses correctedAddresses){

//      client sends a map with the original invalid addresses an the key and
//      the corrected addresses as the value. Match keys and if the value in the map is empty
//      that address was not corrected and can be deleted from the route.

        UnOrganizedRoute unOrganizedRoute = routesManager.getUnorganizedRoute(correctedAddresses.getRouteCode());

        Map<String, List<FormattedAddress>> validatedAddressLists;

        validatedAddressLists = addressesInformationManager.validateAddresses(correctedAddresses.getCorrectedAddressesList());

        List<FormattedAddress> wrongAddressList = validatedAddressLists.get("wrongAddresses");

        if(wrongAddressList.size()>0){

        }else{

        }
    }
}

package controller;

import model.*;
import model.pojos.*;
import model.pojos.openrouteservice.AutocompleteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddressController extends BaseController{

    private final ContainerManager containerManager;
    private final DbManager dbManager;
    private final GoogleMapsApi googleMapsApi;
    private final OpenRouteServiceApi openRouteServiceApi;
    private final GraphhopperApi graphhopperApi;
    private final AddressFormatter addressFormatter;

    private static final Map<Integer, Integer> sessionIds = new HashMap<>();
    private static final Map<Integer, UUID> sessionTokens = new HashMap<>();

    public AddressController() {
        containerManager = getContainerManager();
        dbManager = getDbManager();
        googleMapsApi = getGoogleMapsApi();
        openRouteServiceApi = getOpenRouteServiceApi();
        graphhopperApi = getGraphhopperApi();
        addressFormatter = getAddressFormatter();
    }

    public List<AutocompletePrediction> getAutocomplete(AutocompleteRequest request){

        System.out.println("user id: " + request.getUserId());
        System.out.println("Session id: " + request.getSessionId());
        System.out.println("Query text: " + request.getQueryText());
        System.out.println("User lat: " + request.getUserLocation().getLatitude());
        System.out.println("User lng: " + request.getUserLocation().getLongitude());

        UUID sessionToken;

        int userId = request.getUserId();
        int sessionId = request.getSessionId();

        if(sessionIds.containsKey(userId)){
            if(sessionIds.get(request.getUserId()) == sessionId){
                sessionToken = sessionTokens.get(userId);
            }else{
                sessionIds.remove(userId);
                sessionIds.put(userId, sessionId);
                sessionToken = UUID.randomUUID();
                sessionTokens.remove(userId);
                sessionTokens.put(userId, sessionToken);
            }
        }else{
            sessionIds.put(userId, sessionId);
            sessionToken = UUID.randomUUID();
            sessionTokens.put(userId, sessionToken);
        }

        System.out.println("SessionToken autocomplete: " + sessionToken.toString());
        return googleMapsApi.autocompleteAddress(sessionToken, request.getQueryText(), request.getUserLocation());
    }

    public AddressResponse getAddress(AddressRequest request){

        AddressResponse response = new AddressResponse();
        Address address = new Address();

        if(sessionTokens.get(request.getUserId()) != null){
            System.out.println("SessionToken placeDetails: " + sessionTokens.get(request.getUserId()).toString());
        }

        if(!request.getPlaceId().trim().isEmpty()){
            googleMapsApi.getPlaceDetails(address, request.getPlaceId(), sessionTokens.get(request.getUserId()));
        }else if(!request.getAddress().trim().isEmpty()){
            address.setAddress(request.getAddress());
            googleMapsApi.geocodeAddress(address);
        }

        if(address.isValid()){
            addressFormatter.format(address);
        }

        if(address.isValid()){
            dbManager.getAddressInfo(address);
            response.setValid(true);
            response.setAddress(address);
        }

        return response;
    }

    public Address getAddressDec(AddressRequest request){

        Address address = new Address();
        address.setAddress(request.getAddress());

        validateAddress(address);

        if(address.isValid()) {
            //containerManager.putAddressInList(request.getUsername(), address);
        }

        return address;
    }

    private void validateAddress(Address address){

        String tempAddress = address.getAddress();

//        printLn("Incoming address:" + tempAddress);

        googleMapsApi.geocodeAddress(address);

        if(!address.isValid()) {
            graphhopperApi.geocodeAddress(address);
        }

        if(!address.isValid()) {
            openRouteServiceApi.geocodeAddress(address);
        }

        if(address.isValid()){
            addressFormatter.format(address);

            String street = tempAddress.split(",")[0];

            if(!street.equalsIgnoreCase(address.getStreet())){
                address.setStreet(street);
                address.setAddress(street+", "+address.getPostCode()+" "+address.getCity()+", "+address.getCountry());
//                Removes everything after last digit (Does not work in this case "Nieuwe Binnenweg 271B-01")
//                address.setStreet(address.getStreet().replaceAll("[^\\d]*$", ""));
            }
        }

        if(address.isValid()){
            googleMapsApi.searchForBusinessNearAddress(address);
            googleMapsApi.searchForBusinessNearLocation(address);
            if(address.getBusinessNames() != null && address.getBusinessNames().size() > 0){
                address.setChosenBusinessName(address.getBusinessNames().get(0));
            }
        }

//        if(address.getBusinessName() != null){
//            googleMapsApi.getAddressDetails(address);
//        }

        if(address.isValid()){
            dbManager.getAddressInfo(address);
        }
    }

    public void updatePackageCount(UpdatePackageCountRequest request){

        Container container = containerManager.getContainer(request.getUsername());

        if(container.getAddressList().size() > 0){
            for (Address address : container.getAddressList()) {
                for(String addressString : request.getAddressList()){
                    if(address.getAddress().equals(addressString)){
                        if (address.isValid()) {
                            int count = request.getCountList().get(request.getAddressList().indexOf(addressString));
                            address.setPackageCount(count);
                        }
                        break;
                    }
                }
            }
        }
    }

    public void removeAddress(RemoveAddressRequest request){
//        System.out.println("Removing: "+request.getAddress() +" for user: " + request.getUsername());
        List<Address> addressList = containerManager.getContainer(request.getUsername()).getAddressList();

        for(Address it: addressList){
            if(it.getAddress().equals(request.getAddress())){
                addressList.remove(it);
                break;
            }
        }
    }
}

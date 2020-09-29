package model;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import model.pojos.Address;
import model.pojos.Drive;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GoogleMapsApi {

    private final GeoApiContext context;
    private final AddressFormatter formatter;

    public GoogleMapsApi() {
        //If there are many threads making api request with this key, you might hit the query per second limit! FIX THIS!
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDfhBotxQl1zCKKFSPlbrtipKeV1Yzpg54")
                .build();
        formatter = new AddressFormatter();
    }

    public void verifyAddress(Address address){

        GeocodingResult[] resultsGeo;

        try {
            resultsGeo =  GeocodingApi.geocode(context, address.getAddress()).await();

            if(resultsGeo.length > 0){
                address.setValid(true);
                address.setAddress(resultsGeo[0].formattedAddress);
                address.setLat(resultsGeo[0].geometry.location.lat);
                address.setLng(resultsGeo[0].geometry.location.lng);
            }
        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("verifyAddress in GoogleMapsApi.java: " + address.getAddress());
        }
    }

    public void searchForBusinessNearAddress(Address address){

        PlacesSearchResponse searchResult;

        try {
            searchResult = PlacesApi.textSearchQuery(context, "businesses near " + address.getAddress()).radius(20).await();
            if(searchResult.results.length >= 1){

                for(PlacesSearchResult result : searchResult.results){
                    Address resultAddress = new Address();
                    resultAddress.setAddress(result.formattedAddress);
                    formatter.format(resultAddress);
//                    System.out.println("Name: "+result.name);
//                    System.out.println("");
                    if(resultAddress.getStreet().equals(address.getStreet()) && resultAddress.getCity().equals(address.getCity())){
                        if(address.getPlaceId() == null){
                            address.setPlaceId(new ArrayList<String>());
                        }
                        address.getPlaceId().add(result.placeId);
//                        System.out.println("Name: "+result.name);
//                        System.out.println("Formatted Address: "+result.formattedAddress);
//                        System.out.println("Address: "+address.getAddress());
//                        System.out.println("");
                    }
                }
            }
        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("searchForBusinessNearAddress in GoogleMapsApi.java: " + address.getAddress());
        }
    }

    public void searchForBusinessNearLocation(Address address){

        PlacesSearchResponse searchResult;
        LatLng latLng = new LatLng(address.getLat(), address.getLng());

        try {
            searchResult = PlacesApi.nearbySearchQuery(context, latLng).radius(20).await();
            if(searchResult.results.length>= 1){

                for(PlacesSearchResult result : searchResult.results){
                    if(result.vicinity != null){

                        String street = "";
                        String city = "";

                        if(result.vicinity.contains(",")){
                            String[] vicinityBreakdown = result.vicinity.split(",");
                            street = vicinityBreakdown[0];
                            city = vicinityBreakdown[1];
                            city = city.replaceAll(" ", "");
//                            System.out.println("Street: "+ street);
//                            System.out.println("city: "+ city);
//                            System.out.println("name: "+ result.name);
//                            System.out.println("");
                        }

                        if(street.equals(address.getStreet()) && city.equals(address.getCity())){
                            if(address.getPlaceId() == null){
                                address.setPlaceId(new ArrayList<String>());
                            }
                            address.getPlaceId().add(result.placeId);
//                            System.out.println("Name: "+result.name);
//                            System.out.println("Vicinity: "+result.vicinity);
//                            System.out.println("Address: "+address.getStreet() + ", " + address.getCity());
//                            System.out.println("");
                        }
                    }
                }
            }
        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("searchForBusinessNearLocation in GoogleMapsApi.java: " + address.getAddress());
        }

    }

    public void getAddressDetails(Address address){

        PlaceDetails placeDetails;

        for(String placeId : address.getPlaceId()){
            try {
                placeDetails = PlacesApi.placeDetails(context, placeId).await();
                if(placeDetails != null){
                    if(placeDetails.name != null){

                        address.setBusiness(true);

                        if(address.getBusinessName() == null){
                            address.setBusinessName(new ArrayList<String>());
                        }

                        address.getBusinessName().add(placeDetails.name);

//                        System.out.println("Name: "+placeDetails.name);
//                        System.out.println("");

                        if(placeDetails.openingHours != null){
                            if(address.getWeekdayText() == null){
                                address.setWeekdayText(new HashMap<String, String[]>());
                            }
                            address.getWeekdayText().put(placeDetails.name, placeDetails.openingHours.weekdayText);
                            formatWeekdayText(placeDetails.name, address);
//                          System.out.println(Arrays.toString(placeDetails.openingHours.weekdayText));
                        }
                    }
                }
            } catch (ApiException | IOException | InterruptedException e) {
                System.out.println("getAddressDetails in GoogleMapsApi.java: " + address.getAddress());
            }
        }
    }

    public void formatWeekdayText(String key, Address address) {

        int counter = 0;

        for (String day : address.getWeekdayText().get(key)) {
            String modifiedDay = day.substring(day.indexOf(":") + 2);
            address.getWeekdayText().get(key)[counter] = modifiedDay;
//            System.out.println(address.getWeekdayText().get(key)[counter]);
            counter++;
        }
    }

    public void getDriveInfo(Drive drive) {

        String[] originArray = {drive.getOriginAddress().getAddress()};
        String[] destinationArray = {drive.getDestinationAddress().getAddress()};

        DistanceMatrix resultsDistanceMatrix = null;

        try {
            resultsDistanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, originArray, destinationArray).await();
        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("Distance matrix api request failed for: " + originArray[0] + " - " + destinationArray[0]);
        }

        if(resultsDistanceMatrix != null){
            try {

                drive.getOriginAddress().setAddress(resultsDistanceMatrix.originAddresses[0]);
                drive.getDestinationAddress().setAddress(resultsDistanceMatrix.destinationAddresses[0]);
                drive.setDriveDistanceInMeters(resultsDistanceMatrix.rows[0].elements[0].distance.inMeters);
                drive.setDriveDistanceHumanReadable(resultsDistanceMatrix.rows[0].elements[0].distance.humanReadable);
                drive.setDriveDurationInSeconds(resultsDistanceMatrix.rows[0].elements[0].duration.inSeconds);
                drive.setDriveDurationHumanReadable(resultsDistanceMatrix.rows[0].elements[0].duration.humanReadable);

                drive.setValid(true);
                //System.out.println("From API");
            }catch (NullPointerException e){
                System.out.println("Failed to parse distance matrix api response for: " + originArray[0] + " - " + destinationArray[0]);
            }
        }
    }
}

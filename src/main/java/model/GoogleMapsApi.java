package model;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.*;
import model.pojos.Address;
import model.pojos.Drive;
import model.pojos.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GoogleMapsApi {

    private final GeoApiContext context;
    private final AddressFormatter formatter;

    static int geoRequest = 0;
    static int textSearchQueryRequest = 0;
    static int nearbySearchQueryRequest = 0;

    public GoogleMapsApi() {
        //If there are many threads making api request with this key, you might hit the query per second limit! FIX THIS!
        context = new GeoApiContext.Builder()

                .build();
        formatter = new AddressFormatter();
    }

    public void geocodeAddress(Address address){
        GeocodingResult[] resultsGeo;

        try {
            resultsGeo =  GeocodingApi.geocode(context, address.getAddress()).await();

            if(resultsGeo.length > 0){
                address.setValid(true);
                address.setAddress(resultsGeo[0].formattedAddress);
                address.setLat(resultsGeo[0].geometry.location.lat);
                address.setLng(resultsGeo[0].geometry.location.lng);
                System.out.println("From googlemaps");
            }

        } catch (ApiException | IOException | InterruptedException | IndexOutOfBoundsException e) {
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
                    matchAddressWithCompany(address, resultAddress, result.name);
                }
            }
        } catch (ApiException | IOException | InterruptedException | IndexOutOfBoundsException e) {
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

                            Address resultAddress = new Address();
                            String[] vicinityBreakdown = result.vicinity.split(",");
                            street = vicinityBreakdown[0];
                            city = vicinityBreakdown[1];
                            city = city.replaceAll(" ", "");

                            resultAddress.setAddress(street+", "+city+", Netherlands");
                            formatter.format(resultAddress);
                            matchAddressWithCompany(address, resultAddress, result.name);
                        }

                    }
                }
            }
        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("searchForBusinessNearLocation in GoogleMapsApi.java: " + address.getAddress());
        }

    }

    public void matchAddressWithCompany(Address address, Address resultAddress, String companyName){

//        System.out.println("Formatted address: " + resultAddress.getAddress());
//        System.out.println("");

        String addressStreet = address.getStreet().replaceAll("[^A-Za-z]","").toLowerCase();
        String resultAddressStreet = resultAddress.getStreet().replaceAll("[^A-Za-z]","").toLowerCase();

//        System.out.println("address street given: " + addressStreet);
//        System.out.println("address street found: " + resultAddressStreet);
//        System.out.println("");

        String addressNum = address.getStreet().replaceAll("[^\\d]", "");
        String ResultAddressNum = resultAddress.getStreet().replaceAll("[^\\d]", " ");
        ResultAddressNum = ResultAddressNum.replaceAll(" +", " ");
        ResultAddressNum = ResultAddressNum.trim();
        String[] numBreakdown = ResultAddressNum.split(" ");

//        System.out.println("address num given: " + addressNum);
//        System.out.println(Arrays.toString(numBreakdown));
//        System.out.println("");
//
//        System.out.println("Address city: " + address.getCity());
//        System.out.println("Result city: " + resultAddress.getCity());
//        System.out.println("");

        if(resultAddress.getCity() == null){
            resultAddress.setCity("");
        }

        if (addressStreet.equals(resultAddressStreet) && resultAddress.getCity().contains(address.getCity())) {
            for (String num : numBreakdown) {
                if (num.equals(addressNum)) {
                    addCompanyName(address, companyName);
                }
            }
        }
    }

    public void addCompanyName(Address address, String companyName){
        if(address.getBusinessName() == null){
            address.setBusinessName(new ArrayList<String>());
        }
        if(!address.getBusinessName().contains(companyName) && !companyName.equals(address.getStreet())){
            address.setBusiness(true);
//            System.out.println("Adding: " + companyName);
//            System.out.println("");
            address.getBusinessName().add(companyName);
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
        if(address.getBusinessName() != null && !address.getBusinessName().get(0).isEmpty()){
            address.setChosenBusinessName(address.getBusinessName().get(0));
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

    public void getDirections(Drive drive){

        DirectionsResult directionsResult;

        try {
            directionsResult = DirectionsApi.getDirections(context, drive.getOriginAddress(), drive.getDestinationAddress()).alternatives(false).await();

            if(directionsResult.routes.length >= 1){

//                System.out.println("Origin: " + directionsResult.routes[0].legs[0].arrivalTime);
//                System.out.println("Destination: " + directionsResult.routes[0].legs[0].endAddress);
//                System.out.println("Distance: " + directionsResult.routes[0].legs[0].duration);
//                System.out.println("Duration: " + directionsResult.routes[0].legs[0].distance);

//                drive.setOriginAddress(directionsResult.routes[0].legs[0].startAddress);
//                drive.setDestinationAddress(directionsResult.routes[0].legs[0].endAddress);
                drive.setDriveDistanceHumanReadable(directionsResult.routes[0].legs[0].distance.humanReadable);
                drive.setDriveDistanceInMeters(directionsResult.routes[0].legs[0].distance.inMeters);
                drive.setDriveDurationHumanReadable(directionsResult.routes[0].legs[0].duration.humanReadable);
                drive.setDriveDurationInSeconds(directionsResult.routes[0].legs[0].duration.inSeconds);

                List<Polyline> polyline = new ArrayList<>();

                for(LatLng latLng: PolylineEncoding.decode(directionsResult.routes[0].overviewPolyline.getEncodedPath())){
                    Polyline polylineLatLang = new Polyline();
                    polylineLatLang.setLatitude(latLng.lat);
                    polylineLatLang.setLongitude(latLng.lng);
                    polyline.add(polylineLatLang);
                }

                drive.setPolyline(polyline);

                System.out.println("Directions from Api");

                drive.setValid(true);
            }

        } catch (ApiException | IOException | InterruptedException e) {
            drive.setValid(false);
            System.out.println("getDriveInfo in GoogleMapsApi.java");
        }
    }
}

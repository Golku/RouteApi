package model;

import com.google.gson.JsonSyntaxException;
import model.pojos.Address;
import model.pojos.graphhopper.GeocodingResults;
import retrofit2.Call;

import java.io.IOException;

public class GraphhopperApi {

    private final String apiKey;
    private final GraphhopperApiService graphhopperApiService;

    public GraphhopperApi(GraphhopperApiService graphhopperApiService) {

        this.graphhopperApiService = graphhopperApiService;
    }

    public void geocodeAddress(Address address){

        Call<GeocodingResults> call = graphhopperApiService.geocodingRequest(
                address.getAddress(),
                "en",
                1,
                true,
                apiKey
        );

        GeocodingResults results;

        try {
            results = call.execute().body();

            if (results != null) {

                String street = results.getHits().get(0).getStreet();
                String number = results.getHits().get(0).getHousenumber();
                String postcode = results.getHits().get(0).getPostcode();
                String city = results.getHits().get(0).getCity();
                String country = results.getHits().get(0).getCountry();

                if(street != null){
                    address.setValid(true);
                    address.setAddress(street+" "+ number+ ", " +postcode + " "+ city+", " +country);
                    address.setLat(results.getHits().get(0).getPoint().lat);
                    address.setLng(results.getHits().get(0).getPoint().lng);

                    System.out.println("From graphhopper");
//                System.out.println("Works: " + street + " " + number+ ", " +postcode+ " " +city +", " + country);
                }

            }
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("GraphhopperApi request failed for: ");
        }
    }
}

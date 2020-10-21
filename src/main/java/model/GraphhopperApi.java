package model;

import com.google.gson.JsonSyntaxException;
import model.pojos.Address;
import model.pojos.graphhopper.GeocodingResults;
import model.pojos.graphhopper.RouteOptimizationRequest;
import model.pojos.graphhopper.RouteOptimizationResponse;
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

                if(results.getHits() != null && results.getHits().size() > 0){

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

                        System.out.println("Geocoding from graphhopper");
//                        System.out.println("Works: " + street + " " + number+ ", " +postcode+ " " +city +", " + country);
                    }

                }else{
                    System.out.println("No geocoding results from graphhopper");
                }

            }

        } catch (IOException | JsonSyntaxException e) {
            System.out.println("GraphhopperApi request failed for: ");
        }
    }

    public RouteOptimizationResponse routeOptimization(RouteOptimizationRequest request){

        Call<RouteOptimizationResponse> call = graphhopperApiService.routingRequest(
                request,
                apiKey
        );

        RouteOptimizationResponse routeOptimizationResponse = new RouteOptimizationResponse();
        try {
            routeOptimizationResponse = call.execute().body();
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("GraphhopperApi request failed for: ");
        }
        return routeOptimizationResponse;
    }
}

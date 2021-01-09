package model;

import com.google.gson.JsonSyntaxException;
import model.pojos.Address;
import model.pojos.openrouteservice.*;
import model.services.OpenRouteServiceService;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenRouteServiceApi {

    private final String apiKey = "5b3ce3597851110001cf624899effb59381240569b659067599f6881";
    private final OpenRouteServiceService openRouteServiceService;

    public OpenRouteServiceApi(OpenRouteServiceService openRouteServiceService) {
        this.openRouteServiceService = openRouteServiceService;
    }

    public AutocompleteResponse getAutocomplete(AutocompleteRequest request){

        Call<AutocompleteResponse> call = openRouteServiceService.autocompleteRequest(
                apiKey,
                request.getQueryText(),
                request.getUserLocation().getLatitude(),
                request.getUserLocation().getLongitude(),
                "openaddresses",
                10
        );

        AutocompleteResponse response;

        try {
            response = call.execute().body();

            if (response != null) {

                //

                for(Feature feature: response.getFeatures()){
                    System.out.println("Address: " + feature.properties.getLabel());
                }

                System.out.println("Autocomplete from OpenRouteService");
            }

        } catch (IOException | JsonSyntaxException e) {
            System.out.println("GraphhopperApi request failed for: " + e.getMessage());
        }

        return null;
    }

    public void geocodeAddress(Address address){

        Call<GeocodingResults> call = openRouteServiceService.geocodingRequest(
                apiKey,
                address.getAddress(),
                "NL"
        );

        GeocodingResults results;

        try {
            results = call.execute().body();

            if (results != null) {

            //
                String street = results.getFeatures().get(0).getProperties().getName();
                String postcode = results.getFeatures().get(0).getProperties().getPostalcode();
                String city = results.getFeatures().get(0).getProperties().getLocality();
                String country = results.getFeatures().get(0).getProperties().getCountry();

                address.setValid(true);
                address.setAddress(street+", " +postcode + " "+ city+", " +country);
                address.setLng(results.getFeatures().get(0).getGeometry().coordinates.get(0));
                address.setLat(results.getFeatures().get(0).getGeometry().coordinates.get(1));

                System.out.println("Geocoding from OpenRouteService");
            }

        } catch (IOException | JsonSyntaxException e) {
            System.out.println("OpenRouteService Geocoding request failed for: " + address.getAddress());
        }
    }

    public RouteOptimizationResponse optimizedRoute(Address startLocation, Address endLocation, List<Address> stopList){
        return buildRouteOptimizationRequest(startLocation, endLocation, stopList);
    }

    private RouteOptimizationResponse buildRouteOptimizationRequest(Address startLocation, Address endLocation, List<Address> stopList) {

        RouteOptimizationRequest routeOptimizationRequest = new RouteOptimizationRequest();

        List<Vehicle> vehicles = new ArrayList<>();
        List<Job> jobs = new ArrayList<>();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setProfile("driving-car");

        List<Integer> skills = new ArrayList<>();
        skills.add(1);
        vehicle.setSkills(skills);

        List<Double> start = new ArrayList<>();
        start.add(startLocation.getLng());
        start.add(startLocation.getLat());
        vehicle.setStart(start);

        if(endLocation != null){
            List<Double> end = new ArrayList<>();
            end.add(endLocation.getLng());
            end.add(endLocation.getLat());
            vehicle.setEnd(end);
        }

        vehicles.add(vehicle);

        for(Address address : stopList){
            Job job = new Job();
            job.setId(stopList.indexOf(address));
            job.setService(120);
            List<Double> coordinates = new ArrayList<>();
            coordinates.add(address.getLng());
            coordinates.add(address.getLat());
            job.setLocation(coordinates);
            job.setSkills(skills);
            jobs.add(job);
        }

        routeOptimizationRequest.setVehicles(vehicles);
        routeOptimizationRequest.setJobs(jobs);

        return getOptimizedRoute(routeOptimizationRequest);
    }

    private RouteOptimizationResponse getOptimizedRoute(RouteOptimizationRequest request){

        Call<RouteOptimizationResponse> call = openRouteServiceService.routingRequest(request);

        RouteOptimizationResponse routeOptimizationResponse = new RouteOptimizationResponse();

        try {
            routeOptimizationResponse = call.execute().body();

            //

        } catch (IOException | JsonSyntaxException e) {
            System.out.println("GraphhopperApi request failed for: " + e.getMessage());
        }
        return routeOptimizationResponse;
    }
}

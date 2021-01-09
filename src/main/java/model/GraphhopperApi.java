package model;

import com.google.gson.JsonSyntaxException;
import model.pojos.Address;
import model.pojos.graphhopper.*;
import model.services.GraphhopperApiService;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphhopperApi {

    private final String apiKey = "817cc600-c9a5-4a1f-84dd-357aac783c9d";
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
                    System.out.println("Graphhopper Geocoding request failed for: " + address.getAddress());
                }

            }

        } catch (IOException | JsonSyntaxException e) {
            System.out.println("GraphhopperApi request failed for: ");
        }
    }

    public RouteOptimizationResponse optimizedRoute(Address startLocation, Address endLocation, List<Address> stopList){
        return buildRouteOptimizationRequest(startLocation, endLocation, stopList);
    }

    private RouteOptimizationResponse buildRouteOptimizationRequest(Address startLocation, Address endLocation, List<Address> stopList){

        RouteOptimizationRequest routeOptimizationRequest = new RouteOptimizationRequest();
        RouteOptimizationRequestWithEndAddress routeOptimizationRequestWithEndAddress = new RouteOptimizationRequestWithEndAddress();

        List<Vehicle> vehicles = new ArrayList<>();
        List<VehicleWithEndAddress> vehiclesWithEndAddresses = new ArrayList<>();

        List<Service> services = new ArrayList<>();

        routeOptimizationRequest.setVehicles(vehicles);
        routeOptimizationRequest.setServices(services);
        routeOptimizationRequestWithEndAddress.setVehicles(vehiclesWithEndAddresses);
        routeOptimizationRequestWithEndAddress.setServices(services);

        Vehicle vehicle = new Vehicle();
        VehicleWithEndAddress vehicleWithEndAddress = new VehicleWithEndAddress();
        vehicle.setVehicle_id("my_vehicle");
        vehicleWithEndAddress.setVehicle_id("my_vehicle");

        StartAddress startAddress = new StartAddress();
        startAddress.setLocation_id(startLocation.getAddress());
        startAddress.setLat(startLocation.getLat());
        startAddress.setLon(startLocation.getLng());

        vehicle.setStart_address(startAddress);
        vehicleWithEndAddress.setStart_address(startAddress);

        if (endLocation == null) {
            vehicle.setReturn_to_depot(false);
            routeOptimizationRequestWithEndAddress = null;
        } else {
            EndAddress endAddress = new EndAddress();
            endAddress.setLocation_id(endLocation.getAddress());
            endAddress.setLat(endLocation.getLat());
            endAddress.setLon(endLocation.getLng());
            vehicleWithEndAddress.setEnd_address(endAddress);
            routeOptimizationRequest = null;
        }

        vehicles.add(vehicle);
        vehiclesWithEndAddresses.add(vehicleWithEndAddress);

        for(Address address : stopList){
            Service service = new Service();
            service.setId(address.getAddress());
            service.setName("delivery for: "+address.getAddress());
            model.pojos.graphhopper.Address serviceAddress = new model.pojos.graphhopper.Address();
            serviceAddress.setLocation_id(address.getAddress());
            serviceAddress.setLat(address.getLat());
            serviceAddress.setLon(address.getLng());
            service.setAddress(serviceAddress);
            services.add(service);
        }

        if(routeOptimizationRequest != null){
            return getOptimizedRoute(routeOptimizationRequest);
        }else{
           return getOptimizedRoute(routeOptimizationRequestWithEndAddress);
        }
    }

    private RouteOptimizationResponse getOptimizedRoute(RouteOptimizationRequest request){

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

    private RouteOptimizationResponse getOptimizedRoute(RouteOptimizationRequestWithEndAddress request){

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

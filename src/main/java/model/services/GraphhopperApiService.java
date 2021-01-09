package model.services;

import model.pojos.graphhopper.GeocodingResults;
import model.pojos.graphhopper.RouteOptimizationRequest;
import model.pojos.graphhopper.RouteOptimizationRequestWithEndAddress;
import model.pojos.graphhopper.RouteOptimizationResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface GraphhopperApiService {

    @GET("geocode")
    Call<GeocodingResults> geocodingRequest(@Query("q")String address,
                                            @Query("locale") String locale,
                                            @Query("limit") int limit,
                                            @Query("debug") boolean debug,
                                            @Query("key") String key);

    @Headers("Content-Type: application/json")
    @POST("vrp")
    Call<RouteOptimizationResponse> routingRequest(@Body RouteOptimizationRequest request, @Query("key") String key);

    @Headers("Content-Type: application/json")
    @POST("vrp")
    Call<RouteOptimizationResponse> routingRequest(@Body RouteOptimizationRequestWithEndAddress request, @Query("key") String key);
}

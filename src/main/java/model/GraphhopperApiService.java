package model;

import model.pojos.graphhopper.GeocodingResults;
import model.pojos.graphhopper.RoutingRequest;
import model.pojos.graphhopper.RoutingResult;
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
    Call<RoutingResult> routingRequest(@Body RoutingRequest request);
}

package model.services;

import model.pojos.openrouteservice.AutocompleteResponse;
import model.pojos.openrouteservice.GeocodingResults;
import model.pojos.openrouteservice.RouteOptimizationRequest;
import model.pojos.openrouteservice.RouteOptimizationResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface OpenRouteServiceService {

    @GET("geocode/search")
    Call<GeocodingResults> geocodingRequest(@Query("api_key")String key,
                                            @Query("text") String address,
                                            @Query("boundary.country") String country
    );

    @GET("geocode/autocomplete")
    Call<AutocompleteResponse> autocompleteRequest(@Query("api_key") String key,
                                                   @Query("text") String address,
                                                   @Query("focus.point.lat") double focusPointLat,
                                                   @Query("focus.point.lon") double focusPointLon,
                                                   @Query("sources") String sources,
                                                   @Query("size") int size
    );

    @Headers("Authorization: 5b3ce3597851110001cf624899effb59381240569b659067599f6881")
    @POST("optimization")
    Call<RouteOptimizationResponse> routingRequest(@Body RouteOptimizationRequest request);

}

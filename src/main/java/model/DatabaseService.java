package model;

import model.pojos.DatabaseResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DatabaseService {

    @FormUrlEncoded
    @POST("getAddressBusinessInfo.php")
    Call<DatabaseResponse> getAddressBusinessInfo(
            @Field("street_name") String street,
            @Field("post_code") String postCode,
            @Field("city") String city
    );

    @FormUrlEncoded
    @POST("getTravelInfo.php")
    Call<DatabaseResponse> getTravelInformation(
            @Field("origin_address") String originAddress,
            @Field("destination_address") String destinationAddress
    );

    @FormUrlEncoded
    @POST("addTravelInformation.php")
    Call<DatabaseResponse> addTravelInformation(
            @Field("origin_address") String originAddress,
            @Field("destination_address") String destinationAddress,
            @Field("distance_human_readable") String distanceHumanReadable,
            @Field("distance_in_meters") long distanceInMeters,
            @Field("duration_human_readable") String durationHumanReadable,
            @Field("duration_in_seconds") long durationInSeconds,
            @Field("refresh_date") long refreshDate
    );
}

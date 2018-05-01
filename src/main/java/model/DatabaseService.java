package model;

import model.pojos.DbAddressInfo;
import model.pojos.DbDriveInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DatabaseService {

    @FormUrlEncoded
    @POST("getAddressBusinessInfo.php")
    Call<DbAddressInfo> getAddressInfo(
            @Field("street_name") String street,
            @Field("post_code") String postCode,
            @Field("city") String city
    );

    @FormUrlEncoded
    @POST("getDriveInfo.php")
    Call<DbDriveInfo> getDriveInfo(
            @Field("origin_address") String originAddress,
            @Field("destination_address") String destinationAddress
    );

    @FormUrlEncoded
    @POST("addDriveInfo.php")
    Call<Void> addDriveInfo(
            @Field("origin_address") String originAddress,
            @Field("destination_address") String destinationAddress,
            @Field("distance_in_meters") long distanceInMeters,
            @Field("distance_human_readable") String distanceHumanReadable,
            @Field("duration_in_seconds") long durationInSeconds,
            @Field("duration_human_readable") String durationHumanReadable,
            @Field("refresh_date") long refreshDate
    );
}

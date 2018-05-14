package model;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import model.pojos.Address;
import model.pojos.DbAddressInfo;
import model.pojos.FormattedAddress;
import model.pojos.Drive;
import retrofit2.Call;

import java.io.IOException;

public class GoogleMapsApi {

    private GeoApiContext context;

    public GoogleMapsApi() {
        //If there are many threads making api request with this key, you might hit the query per second limit! FIX THIS!
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDfhBotxQl1zCKKFSPlbrtipKeV1Yzpg54")
                .build();
    }

    public void verifyAddress(Address address){

        GeocodingResult[] resultsGeo;

        try {
            resultsGeo =  GeocodingApi.geocode(context, address.getAddress()).await();

            if(resultsGeo.length > 0){
                address.setValid(true);
                address.setAddress(resultsGeo[0].formattedAddress);
                address.setLat(resultsGeo[0].geometry.location.lat);
                address.setLng(resultsGeo[0].geometry.location.lng);
            }
        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("verifyAddress in GoogleMapsApi.java was unable to validate: " + address.getAddress());
        }
    }

    public void getDriveInfo(Drive drive) {

        String[] originArray = {drive.getOriginAddress().getAddress()};
        String[] destinationArray = {drive.getDestinationAddress().getAddress()};

        Call<Void> call = null;
        DistanceMatrix resultsDistanceMatrix = null;

        try {
            resultsDistanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, originArray, destinationArray).await();
        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("Distance matrix api request failed for: " + originArray[0] + " - " + destinationArray[0]);
        }

        if(resultsDistanceMatrix != null){
            try {

                drive.getOriginAddress().setAddress(resultsDistanceMatrix.originAddresses[0]);
                drive.getDestinationAddress().setAddress(resultsDistanceMatrix.destinationAddresses[0]);
                drive.setDriveDistanceInMeters(resultsDistanceMatrix.rows[0].elements[0].distance.inMeters);
                drive.setDriveDistanceHumanReadable(resultsDistanceMatrix.rows[0].elements[0].distance.humanReadable);
                drive.setDriveDurationInSeconds(resultsDistanceMatrix.rows[0].elements[0].duration.inSeconds);
                drive.setDriveDurationHumanReadable(resultsDistanceMatrix.rows[0].elements[0].duration.humanReadable);

                drive.setValid(true);
            }catch (NullPointerException e){
                System.out.println("Failed to parse distance matrix api response for: " + originArray[0] + " - " + destinationArray[0]);
            }
        }
    }
}

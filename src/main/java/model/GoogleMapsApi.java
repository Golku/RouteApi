package model;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlaceDetails;
import model.pojos.DatabaseResponse;
import model.pojos.FormattedAddress;
import model.pojos.SingleDrive;
import retrofit2.Call;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GoogleMapsApi {

    private GeoApiContext context;
    private AddressFormatter addressFormatter;
    private DatabaseService databaseService;
    private String origin;
    private String destination;
    private long date;

    public GoogleMapsApi(DatabaseService databaseService, AddressFormatter addressFormatter) {
        date = System.currentTimeMillis();
        this.addressFormatter = addressFormatter;
        this.databaseService = databaseService;

        //If there are many threads making api request with this key, you might hit the query per second limit! FIX THIS!
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDfhBotxQl1zCKKFSPlbrtipKeV1Yzpg54")
                .build();
    }

    public void verifyAddress(FormattedAddress address){

        GeocodingResult[] resultsGeo;

        try {
            resultsGeo =  GeocodingApi.geocode(context, address.getRawAddress()).await();

            if(resultsGeo.length > 0){
                address.setFormattedAddress(resultsGeo[0].formattedAddress);
                address.setLat(resultsGeo[0].geometry.location.lat);
                address.setLng(resultsGeo[0].geometry.location.lng);
            }else{
                address.setInvalid(true);
            }

        } catch (ApiException | IOException | InterruptedException e) {
            System.out.println("verifyAddress in GoogleMapsApi.java was unable to validate: " + address);
        }
    }

    public SingleDrive getDriveInformation(String origin, String destination){

        this.origin = origin;
        this.destination = destination;

        SingleDrive singleDrive = getDriveInfoFromDb();

        if (singleDrive == null) {
            singleDrive = getDriveInfoFromGoogleApi();
        }

        return singleDrive;
    }

    private SingleDrive getDriveInfoFromDb(){

        SingleDrive singleDrive = null;
        DatabaseResponse databaseResponse = null;

        Call<DatabaseResponse> call = databaseService.getTravelInformation(origin, destination);

        try {
            databaseResponse = call.execute().body();
        } catch (IOException e) {
//                e.printStackTrace();
            System.out.println("Database request failed for: " + origin + " - " + destination);
        }

        if(databaseResponse != null){
            if(databaseResponse.isInformationAvailable()){

                long refreshDate = databaseResponse.getTravelInformation().getRefreshDate();

                if(date < refreshDate) {

                    singleDrive = new SingleDrive();

                    try {
                        String originAddress = databaseResponse.getTravelInformation().getOriginAddress();
                        String destinationAddress = databaseResponse.getTravelInformation().getDestinationAddress();
                        String driveDistanceHumanReadable = databaseResponse.getTravelInformation().getDistanceHumanReadable();
                        long driveDistanceInMeters = databaseResponse.getTravelInformation().getDistanceInMeters();
                        String driveDurationHumanReadable = databaseResponse.getTravelInformation().getDurationHumanReadable();
                        long driveDurationInSeconds = databaseResponse.getTravelInformation().getDurationInSeconds();

                        FormattedAddress originF = new FormattedAddress();
                        originF.setFormattedAddress(originAddress);
                        FormattedAddress destinationF = new FormattedAddress();
                        destinationF.setFormattedAddress(destinationAddress);
                        addressFormatter.orderAddress(originF);
                        addressFormatter.orderAddress(destinationF);


                        singleDrive.setOriginFormattedAddress(originF);
                        singleDrive.setDestinationFormattedAddress(destinationF);
                        singleDrive.setDriveDistanceHumanReadable(driveDistanceHumanReadable);
                        singleDrive.setDriveDistanceInMeters(driveDistanceInMeters);
                        singleDrive.setDriveDurationHumanReadable(driveDurationHumanReadable);
                        singleDrive.setDriveDurationInSeconds(driveDurationInSeconds);

                        System.out.println("Travel information from db for: " + origin + " - " + destination);
                    }catch (NullPointerException e){
                        System.out.println("Null object at GoogleMapsApi.java at block 99");
                        singleDrive = null;
                    }


                }
            }
        }

        return singleDrive;
    }

    private SingleDrive getDriveInfoFromGoogleApi(){

        SingleDrive singleDrive = null;
        DistanceMatrix resultsDistanceMatrix = null;

        String[] originArray = {origin};
        String[] destinationArray = {destination};

        try {
            resultsDistanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, originArray, destinationArray).await();
        } catch (ApiException | IOException | InterruptedException e) {
//                    e.printStackTrace();
            System.out.println("Distance matrix api request failed for: " + origin + " - " + destination);
        }

        if(resultsDistanceMatrix != null){

            singleDrive = new SingleDrive();
            Call<DatabaseResponse> call = null;

            try {

                FormattedAddress originF = new FormattedAddress();
                originF.setFormattedAddress(resultsDistanceMatrix.originAddresses[0]);
                FormattedAddress destinationF = new FormattedAddress();
                destinationF.setFormattedAddress(resultsDistanceMatrix.destinationAddresses[0]);
                addressFormatter.orderAddress(originF);
                addressFormatter.orderAddress(destinationF);

                singleDrive.setOriginFormattedAddress(originF);
                singleDrive.setDestinationFormattedAddress(destinationF);
                singleDrive.setDriveDistanceHumanReadable(resultsDistanceMatrix.rows[0].elements[0].distance.humanReadable);
                singleDrive.setDriveDistanceInMeters(resultsDistanceMatrix.rows[0].elements[0].distance.inMeters);
                singleDrive.setDriveDurationHumanReadable(resultsDistanceMatrix.rows[0].elements[0].duration.humanReadable);
                singleDrive.setDriveDurationInSeconds(resultsDistanceMatrix.rows[0].elements[0].duration.inSeconds);

                call = databaseService.addTravelInformation(
                        origin,
                        destination,
                        singleDrive.getDriveDistanceHumanReadable(),
                        singleDrive.getDriveDistanceInMeters(),
                        singleDrive.getDriveDurationHumanReadable(),
                        singleDrive.getDriveDurationInSeconds(),
                        date + 432000000 //current date + 5 days in milliseconds
                );

                System.out.println("Travel information from maps api for: " + origin + " - " + destination);
            }catch (NullPointerException e){
                System.out.println("Null object at GoogleMapsApi.java at block 146");
                singleDrive = null;
            }

            try {
                if (call != null) {
                    call.execute();
                    System.out.println("Travel information added to database");
                }
            } catch (IOException e){
//                e.printStackTrace();
                System.out.println("unable to add travel information to db: " + origin + " - " + destination);
            }
        }
        return singleDrive;
    }

}

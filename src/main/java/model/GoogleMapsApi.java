package model;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlaceDetails;
import model.pojos.FormattedAddress;
import model.pojos.SingleDrive;

import java.io.IOException;

public class GoogleMapsApi {

    GeoApiContext context;
    AddressFormatter addressFormatter;

    public GoogleMapsApi(AddressFormatter addressFormatter) {
        this.addressFormatter = addressFormatter;
        //If there are many threads making api request with this key, you might hit the query per second limit! FIX THIS!
        this.context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDfhBotxQl1zCKKFSPlbrtipKeV1Yzpg54")
                .build();
    }

    public FormattedAddress validatedAddress(String address){

        String validatedAddress = null;
        GeocodingResult[] resultsGeo;

        try {

            resultsGeo =  GeocodingApi.geocode(context,address).await();

            if(resultsGeo.length > 0){
                validatedAddress = resultsGeo[0].formattedAddress;
            }

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressFormatter.formatAddress(validatedAddress);
    }

    public SingleDrive getDriveInformation(String origin, String destination){

        SingleDrive singleDrive = new SingleDrive();

        String[] originArray = {origin};
        String[] destinationArray = {destination};

        DistanceMatrix resultsDistanceMatrix = null;

        try {
            resultsDistanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, originArray, destinationArray).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Improve logic to prevent errors when resultsDistanceMatrix == null! FIX THIS!
        if(resultsDistanceMatrix == null){

        }else{

            singleDrive.setOriginFormattedAddress(addressFormatter.formatAddress(resultsDistanceMatrix.originAddresses[0]));
            singleDrive.setDestinationFormattedAddress(addressFormatter.formatAddress(resultsDistanceMatrix.destinationAddresses[0]));

            singleDrive.setDriveDistanceHumanReadable(resultsDistanceMatrix.rows[0].elements[0].distance.humanReadable);
            singleDrive.setDriveDistanceInMeters(resultsDistanceMatrix.rows[0].elements[0].distance.inMeters);
            singleDrive.setDriveDurationHumanReadable(resultsDistanceMatrix.rows[0].elements[0].duration.humanReadable);
            singleDrive.setDriveDurationInSeconds(resultsDistanceMatrix.rows[0].elements[0].duration.inSeconds);

        }

        return singleDrive;

//        System.out.println(oneDriveInformation.getOriginStreet());
//        System.out.println(oneDriveInformation.getOriginPostCode());
//        System.out.println(oneDriveInformation.getOriginCity());
//        System.out.println(oneDriveInformation.getOriginCountry());


//        System.out.println(singleDrive.getDestinationCompleteAddress());
//        System.out.println(oneDriveInformation.getDestinationStreet());
//        System.out.println(oneDriveInformation.getDestinationPostCode());
//        System.out.println(oneDriveInformation.getDestinationCity());
//        System.out.println(oneDriveInformation.getDestinationCountry());

//        System.out.println(singleDrive.getDriveDurationHumanReadable());
//        System.out.println(singleDrive.getDriveDistanceHumanReadable());

//        System.out.println(singleDrive.getDriveDurationInSeconds());
//        System.out.println(singleDrive.getdriveDistanceInMeters());

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(results.rows[0].elements[0].durationInTraffic));

    }

}

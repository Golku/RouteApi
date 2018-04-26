package controller;

import com.google.gson.Gson;
import model.AddressesInformationManager;
import model.DatabaseService;
import model.GoogleMapsApi;
import model.pojos.SingleDrive;
import model.pojos.SingleDriveRequest;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriveInfoController {

    GoogleMapsApi googleMapsApi;
    AddressesInformationManager addressesInformationManager;

    public DriveInfoController() {
        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)//192.168.0.16
                .baseUrl("http://192.168.0.16/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        DatabaseService databaseService = retrofit.create(DatabaseService.class);

        googleMapsApi = new GoogleMapsApi(databaseService);
        addressesInformationManager = new AddressesInformationManager(googleMapsApi, databaseService);
    }

    public SingleDrive getDriveInformation(SingleDriveRequest request){

        SingleDrive singleDrive = googleMapsApi.getDriveInformation(request.getOrigin(), request.getDestination());

        addressesInformationManager.getAddressType(singleDrive.getDestinationFormattedAddress());

        if(singleDrive.getDestinationFormattedAddress().getIsBusiness()){
            singleDrive.setDestinationIsABusiness(true);
        }

        return singleDrive;
    }

}

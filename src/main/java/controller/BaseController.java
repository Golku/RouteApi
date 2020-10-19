package controller;

import com.google.gson.Gson;
import model.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class BaseController {

    GoogleMapsApi getGoogleMapsApi(){
        return new GoogleMapsApi();
    }

    GraphhopperApi getGraphhopperApi(){
        return new GraphhopperApi(getGraphhopperApiService());
    }

    ContainerManager getContainerManager(){
        return new ContainerManager();
    }

    AddressFormatter getAddressFormatter(){
        return new AddressFormatter();
    }

    DbManager getDbManager(){
        return new DbManager(getDatabaseService());
    }

    private DatabaseService getDatabaseService(){

        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)//192.168.0.16
                .baseUrl("http://192.168.178.164/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(DatabaseService.class);
    }

    private GraphhopperApiService getGraphhopperApiService(){

        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)//192.168.0.16
                .baseUrl("https://graphhopper.com/api/1/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(GraphhopperApiService.class);
    }

}

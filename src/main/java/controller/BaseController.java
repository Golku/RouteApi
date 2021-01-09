package controller;

import com.google.gson.Gson;
import model.*;
import model.services.DatabaseService;
import model.services.GraphhopperApiService;
import model.services.OpenRouteServiceService;
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

    OpenRouteServiceApi getOpenRouteServiceApi(){return  new OpenRouteServiceApi(getOpenRouteServiceApiService());}

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
                .client(okHttpClient)
                .baseUrl("http://192.168.178.164/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(DatabaseService.class);
    }

    private GraphhopperApiService getGraphhopperApiService(){

        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://graphhopper.com/api/1/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(GraphhopperApiService.class);
    }

    private OpenRouteServiceService getOpenRouteServiceApiService(){

        Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openrouteservice.org/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(OpenRouteServiceService.class);
    }

    public void printLn(String message){
        System.out.println(message);
    }
}

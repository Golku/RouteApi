package controller;

import com.google.gson.Gson;
import model.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class BaseController {

    RouteManager getRouteManager(){
        return new RouteManager();
    }

    GoogleMapsApi getGoogleMapsApi(){
        return new GoogleMapsApi();
    }

    RoutesOrganizer getRoutesOrganizer(){
//        return new RoutesOrganizer();
        return null;
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
                .baseUrl("http://192.168.0.16/map/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(DatabaseService.class);
    }
}

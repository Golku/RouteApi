package model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.pojos.DatabaseResponse;
import model.pojos.FormattedAddress;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressesInformationManager {

    private GoogleMapsApi googleMapsApi;

    private final String root_url = "http://192.168.0.14/map/v1/";
    private final String url_getAddressInfo = root_url + "getAddressBusinessInfo.php";

    public AddressesInformationManager(GoogleMapsApi googleMapsApiInstance) {
        this.googleMapsApi = googleMapsApiInstance;
    }

    public Map<String, List<FormattedAddress>> validateAddresses(List<String> addressList){

        Map<String, List<FormattedAddress>> validatedAddressLists = new HashMap<>();
        List<FormattedAddress> validatedAddressList = new ArrayList<>();
        List<FormattedAddress> wrongAddressList = new ArrayList<>();

        for(int i=0; i<addressList.size(); i++){

            FormattedAddress verifiedAddress = googleMapsApi.validatedAddress(addressList.get(i));

            if(verifiedAddress.isInvalid()) {
                System.out.println("Wrong: "+verifiedAddress.getRawAddress());
                wrongAddressList.add(verifiedAddress);
            }else{

                String country = verifiedAddress.getCountry();

                if(country.equals("Netherlands")){
                    System.out.println("Validated: "+verifiedAddress.getRawAddress());
                    validatedAddressList.add(verifiedAddress);
                }else{
//                    Send the original inputed address to the client as well
//                    addressList.get(i);
                    System.out.println("Wrong: "+verifiedAddress.getRawAddress());
                    wrongAddressList.add(verifiedAddress);
                }

            }

        }

        validatedAddressLists.put("validAddresses", validatedAddressList);
        validatedAddressLists.put("wrongAddresses", wrongAddressList);

        return validatedAddressLists;
    }

    public List<FormattedAddress> findBusinessAddresses(List<FormattedAddress> validatedAddressList) {

        List<FormattedAddress> businessAddressList = new ArrayList<>();

        final Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        for (int i=0; i<validatedAddressList.size(); i++) {

            String street = validatedAddressList.get(i).getStreet();
            String postCode = validatedAddressList.get(i).getPostCode();
            String city = validatedAddressList.get(i).getCity();

            Request request = new Request.Builder()
                    .url(url_getAddressInfo + "?"
                            + "street_name=" + street +"&"
                            + "post_code=" + postCode +"&"
                            + "city=" + city
                    )
                    .get()
                    .build();

            Response response;
            String responseString = null;

            try {
                response = okHttpClient.newCall(request).execute();
                responseString = response.body().string();
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("Database request failed for: " + street +", "+ postCode +" "+ city);
            }

            DatabaseResponse databaseResponse = null;

            try{
                databaseResponse = gson.fromJson(responseString, DatabaseResponse.class);
            }catch (JsonSyntaxException e){
                System.out.println("Response failed: "+ e);
            }

            if(databaseResponse != null) {
                if (!databaseResponse.isError()) {
                    if (databaseResponse.getBusiness() == 1) {
                        validatedAddressList.get(i).setIsBusiness(true);
                        businessAddressList.add(validatedAddressList.get(i));
                    }
                } else {
                    System.out.println(databaseResponse.getErrorMessage());
                }
            }else{
                System.out.println("Database response is null");
            }
        }

        return businessAddressList;

    }

    public List<FormattedAddress> findPrivateAddresses(List<FormattedAddress> validatedAddressList){

        List<FormattedAddress> privateAddressList = new ArrayList<>();

        for(int i =0; i<validatedAddressList.size(); i++){

            if(!validatedAddressList.get(i).getIsBusiness()){
                privateAddressList.add(validatedAddressList.get(i));
            }

        }

        return privateAddressList;
    }

}

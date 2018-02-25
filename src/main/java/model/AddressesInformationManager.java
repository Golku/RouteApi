package model;

import com.google.gson.Gson;
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

    private final String root_url = "http://217.103.231.118/map/v1/";
    private final String url_getAddressInfo = root_url + "getAddressBusinessInfo.php";

    private String street;
    private String postCode;
    private String city;

    private Map<String, List<FormattedAddress>> validatedAddressLists = new HashMap<>();
    private List<FormattedAddress> validatedAddressList = new ArrayList<>();
    private List<FormattedAddress> privateAddressList = new ArrayList<>();
    private List<FormattedAddress> businessAddressList = new ArrayList<>();
    private List<FormattedAddress> wrongAddressList = new ArrayList<>();


    public AddressesInformationManager(GoogleMapsApi googleMapsApiInstance) {
        this.googleMapsApi = googleMapsApiInstance;
    }

    public Map<String, List<FormattedAddress>> validateAddresses(List<String> addressList){

        for(int i=0; i<addressList.size(); i++){

            FormattedAddress verifiedAddress = googleMapsApi.validatedAddress(addressList.get(i));

            if(verifiedAddress.isInvalid()) {
                wrongAddressList.add(verifiedAddress);
            }else{
                validatedAddressList.add(verifiedAddress);
            }

        }

        validatedAddressLists.put("validAddresses", validatedAddressList);
        validatedAddressLists.put("wrongAddresses", wrongAddressList);

        return validatedAddressLists;
    }

    public List<FormattedAddress> findBusinessAddresses() {

        final Gson gson = new Gson();

        OkHttpClient okHttpClient = new OkHttpClient();

        for (int i=0; i<validatedAddressList.size(); i++){

            street = validatedAddressList.get(i).getStreet();
            postCode = validatedAddressList.get(i).getPostCode();
            city = validatedAddressList.get(i).getCity();

            Request request = new Request.Builder()
                    .url(url_getAddressInfo+"?"
                            +"street_name="+street
                            +"post_code="+postCode
                            +"city="+city
                    )
                    .build();

            Response response;
            String responseString = null;

            try {
                response = okHttpClient.newCall(request).execute();
                responseString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            DatabaseResponse databaseResponse = gson.fromJson(responseString, DatabaseResponse.class);

            if(!databaseResponse.isError()){
                if(databaseResponse.getBusiness() == 1){
                    validatedAddressList.get(i).setIsBusiness(true);
                    businessAddressList.add(validatedAddressList.get(i));
                }
            }else{
                System.out.println(databaseResponse.getErrorMessage());
            }

        }

        return businessAddressList;

    }

    public List<FormattedAddress> findPrivateAddresses(){

        for(int i =0; i<validatedAddressList.size(); i++){

            if(!validatedAddressList.get(i).getIsBusiness()){
                privateAddressList.add(validatedAddressList.get(i));
            }

        }

        return privateAddressList;
    }

}

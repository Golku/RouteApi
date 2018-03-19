package model;

import model.pojos.DatabaseResponse;
import model.pojos.FormattedAddress;
import retrofit2.Call;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressesInformationManager {

    private GoogleMapsApi googleMapsApi;

    private DatabaseService databaseService;

    public AddressesInformationManager(GoogleMapsApi googleMapsApiInstance, DatabaseService databaseService) {
        this.googleMapsApi = googleMapsApiInstance;
        this.databaseService = databaseService;
    }

    public Map<String, List<FormattedAddress>> validateAddresses(List<String> addressList){

        Map<String, List<FormattedAddress>> validatedAddressLists = new HashMap<>();
        List<FormattedAddress> validatedAddressList = new ArrayList<>();
        List<FormattedAddress> wrongAddressList = new ArrayList<>();

        for(int i=0; i<addressList.size(); i++){

            FormattedAddress verifiedAddress = googleMapsApi.validatedAddress(addressList.get(i));

            if(verifiedAddress != null) {

                if (verifiedAddress.isInvalid()) {
                    System.out.println("Wrong: " + verifiedAddress.getRawAddress());
                    wrongAddressList.add(verifiedAddress);
                } else {

                    String country = verifiedAddress.getCountry();

                    if (country.equals("Netherlands")) {
                        System.out.println("Validated: " + verifiedAddress.getRawAddress());
                        validatedAddressList.add(verifiedAddress);
                    } else {
//                    Send the original inputed address to the client as well
//                    addressList.get(i);
                        System.out.println("Wrong: " + verifiedAddress.getRawAddress());
                        wrongAddressList.add(verifiedAddress);
                    }

                }

            }else{
                System.out.println("The address is null");
            }

        }

        validatedAddressLists.put("validAddresses", validatedAddressList);
        validatedAddressLists.put("wrongAddresses", wrongAddressList);

        return validatedAddressLists;
    }

    public List<FormattedAddress> findBusinessAddresses(List<FormattedAddress> validatedAddressList) {

        List<FormattedAddress> businessAddressList = new ArrayList<>();

        for (int i=0; i<validatedAddressList.size(); i++) {

            String street = validatedAddressList.get(i).getStreet();
            String postCode = validatedAddressList.get(i).getPostCode();
            String city = validatedAddressList.get(i).getCity();

            Call<DatabaseResponse> call = databaseService.getAddressBusinessInfo(
                    street,
                    postCode,
                    city
            );

            DatabaseResponse databaseResponse = null;

            try {
                databaseResponse = call.execute().body();
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("Database request failed for: " + street +", "+ postCode +" "+ city);
            }

            if(databaseResponse != null) {
                if (!databaseResponse.isError()) {
                    if (databaseResponse.getBusiness() == 1) {
                        validatedAddressList.get(i).setIsBusiness(true);
                        businessAddressList.add(validatedAddressList.get(i));
                    }
                } else {
//                    System.out.println(databaseResponse.getErrorMessage());
                }
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

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

    public Map<String, List<FormattedAddress>> validateAddressList(List<String> addressList){

        Map<String, List<FormattedAddress>> validatedAddressLists = new HashMap<>();
        List<FormattedAddress> validatedAddressList = new ArrayList<>();
        List<FormattedAddress> wrongAddressList = new ArrayList<>();

        for (String address : addressList) {

            FormattedAddress verifiedAddress = googleMapsApi.validatedAddress(address);

            if (verifiedAddress != null) {

                if (verifiedAddress.isInvalid()) {
//                    System.out.println("Wrong address: " + verifiedAddress.getRawAddress());
                    wrongAddressList.add(verifiedAddress);
                } else {

                    String country = verifiedAddress.getCountry();

                    if (country.equals("Netherlands")) {
//                        System.out.println("Validated: " + verifiedAddress.getFormattedAddress());
                        validatedAddressList.add(verifiedAddress);
                    } else {
//                    Send the original inputed address to the client as well
//                        System.out.println("Wrong country: " + verifiedAddress.getRawAddress());
                        wrongAddressList.add(verifiedAddress);
                    }

                }
            } else {
                System.out.println("GoogleMapsApi/validatedAddress returned null");
            }
        }

        validatedAddressLists.put("validAddresses", validatedAddressList);
        validatedAddressLists.put("wrongAddresses", wrongAddressList);

        return validatedAddressLists;
    }

    public List<FormattedAddress> findBusinessAddresses(List<FormattedAddress> validatedAddressList) {

        List<FormattedAddress> businessAddressList = new ArrayList<>();

        for (FormattedAddress formattedAddress : validatedAddressList) {

            String street = formattedAddress.getStreet();
            String postCode = formattedAddress.getPostCode();
            String city = formattedAddress.getCity();

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
                System.out.println("Database request failed for: " + street + ", " + postCode + " " + city);
            }

            if (databaseResponse != null) {
                if (!databaseResponse.isError()) {
                    if (databaseResponse.getBusiness() == 1) {
                        formattedAddress.setIsBusiness(true);
                        businessAddressList.add(formattedAddress);
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

        for (FormattedAddress formattedAddress : validatedAddressList) {

            if (!formattedAddress.getIsBusiness()) {
                privateAddressList.add(formattedAddress);
            }

        }

        return privateAddressList;
    }

}

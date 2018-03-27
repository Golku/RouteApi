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

    public Map<String, ArrayList<FormattedAddress>> validateAddressList(List<String> addressList){

        Map<String, ArrayList<FormattedAddress>> validatedAddressLists = new HashMap<>();
        ArrayList<FormattedAddress> validAddressList = new ArrayList<>();
        ArrayList<FormattedAddress> invalidAddressList = new ArrayList<>();

        for (String address : addressList) {

            FormattedAddress verifiedAddress = googleMapsApi.validatedAddress(address);

            if (verifiedAddress != null) {

                if (verifiedAddress.isInvalid()) {
//                    System.out.println("Wrong address: " + verifiedAddress.getRawAddress());
                    invalidAddressList.add(verifiedAddress);
                } else {

                    String country = verifiedAddress.getCountry();

                    if (country.equals("Netherlands")) {
//                        System.out.println("Validated: " + verifiedAddress.getFormattedAddress());
                        validAddressList.add(verifiedAddress);
                    } else {
//                        Send the original inputed address to the client as well
//                        System.out.println("Wrong country: " + verifiedAddress.getRawAddress());
                        invalidAddressList.add(verifiedAddress);
                    }

                }
            }
        }

        validatedAddressLists.put("validAddresses", validAddressList);
        validatedAddressLists.put("invalidAddresses", invalidAddressList);

        return validatedAddressLists;
    }

    public List<FormattedAddress> findBusinessAddresses(List<FormattedAddress> validAddressList) {

        List<FormattedAddress> businessAddressList = new ArrayList<>();

        for (FormattedAddress formattedAddress : validAddressList) {

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

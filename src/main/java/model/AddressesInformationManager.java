package model;

import model.pojos.DatabaseResponse;
import model.pojos.FormattedAddress;
import retrofit2.Call;

import java.io.IOException;

public class AddressesInformationManager {

    private GoogleMapsApi googleMapsApi;
    private AddressFormatter addressFormatter;
    private DatabaseService databaseService;

    public AddressesInformationManager(GoogleMapsApi googleMapsApiInstance, DatabaseService databaseService, AddressFormatter addressFormatter) {
        this.googleMapsApi = googleMapsApiInstance;
        this.databaseService = databaseService;
        this.addressFormatter = addressFormatter;
    }

    public FormattedAddress validateAddress(String address) {
        //Log the address send for validation and the one received

        FormattedAddress formattedAddress = new FormattedAddress();
        formattedAddress.setRawAddress(address);

        googleMapsApi.verifyAddress(formattedAddress);

        if(!formattedAddress.isInvalid()) {
            addressFormatter.orderAddress(formattedAddress);
        }

        if(!formattedAddress.isInvalid()){
            if(!formattedAddress.getCountry().equals("Netherlands")){
                formattedAddress.setInvalid(true);
            }
        }

        return formattedAddress;
    }

    public void setAddressType(FormattedAddress address) {

        String street = address.getStreet();
        String postCode = address.getPostCode();
        String city = address.getCity();

        Call<DatabaseResponse> call = databaseService.getAddressBusinessInfo(
                street,
                postCode,
                city
        );

        DatabaseResponse databaseResponse = null;

        try {
            databaseResponse = call.execute().body();
        } catch (IOException e) {
            System.out.println("Database request failed for: " + street + ", " + postCode + " " + city);
        }

        if (databaseResponse != null) {
            if (!databaseResponse.isError()) {
                if (databaseResponse.getBusiness() == 1) {
                    address.setBusiness(true);
                }
            }
        }
    }
}

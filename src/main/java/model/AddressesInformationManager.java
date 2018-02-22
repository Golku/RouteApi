package model;
import model.pojos.FormattedAddress;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressesInformationManager {

    private GoogleMapsApi googleMapsApi;

    //Check if declaring within the try and catch is better and if so, move it there.
    //Check how long this variable lives in memory
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private Map<String, List<FormattedAddress>> validatedAddressLists = new HashMap<>();
    private List<FormattedAddress> validatedAddressList = new ArrayList<>();
    private List<FormattedAddress> privateAddressList = new ArrayList<>();
    private List<FormattedAddress> businessAddressList = new ArrayList<>();
    private List<FormattedAddress> wrongAddressList = new ArrayList<>();

    private String street;
    private String postCode;
    private String city;

    public AddressesInformationManager(GoogleMapsApi googleMapsApiInstance) {

        this.googleMapsApi = googleMapsApiInstance;

        try {
            //This has to be unregistered at the end of the thread! FIX THIS!
            Class.forName("com.mysql.cj.jdbc.Driver");
            //When connection fails application crashes. FIX THIS!
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/map", "root", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Map<String, List<FormattedAddress>> validateAddresses(List<String> addressList){

        for(int i=0; i<addressList.size(); i++){

            FormattedAddress verifiedAddress = googleMapsApi.validatedAddress(addressList.get(i));

            if(verifiedAddress.isInvalid()) {
//                System.out.println("Wrong: "+verifiedAddress.getRawAddress());
//                System.out.println("");
                wrongAddressList.add(verifiedAddress);
            }else{
//                System.out.println("Validated raw: "+verifiedAddress.getRawAddress());
//                System.out.println("Validated formatted: "+verifiedAddress.getFormattedAddress());
//                System.out.println("");
                validatedAddressList.add(verifiedAddress);
            }

        }

        validatedAddressLists.put("validAddresses", validatedAddressList);
        validatedAddressLists.put("wrongAddresses", wrongAddressList);

        return validatedAddressLists;
    }

    public List<FormattedAddress> findBusinessAddresses() {

        for (int i=0; i<validatedAddressList.size(); i++){

            street = validatedAddressList.get(i).getStreet();
            postCode = validatedAddressList.get(i).getPostCode();
            city = validatedAddressList.get(i).getCity().replaceAll(" ", "_");

            try {

//                System.out.println("SQL");
//                System.out.println(validatedAddressList.get(i).getCompletedAddress());
//                System.out.println(street);
//                System.out.println(postCode);
//                System.out.println(city);

                resultSet = statement.executeQuery("SELECT business FROM "+city+" WHERE street_name = '"+street+"' AND post_code ='"+postCode+"'");

                while (resultSet.next()) {

                    if(resultSet.getBoolean("business")) {
                        validatedAddressList.get(i).setIsBusiness(true);
                        businessAddressList.add(validatedAddressList.get(i));
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return businessAddressList;

    }

    public List<FormattedAddress> findPrivateAddresses(){

        for(int i =0; i<validatedAddressList.size(); i++){

            if(validatedAddressList.get(i).getIsBusiness() == false){
                privateAddressList.add(validatedAddressList.get(i));
            }

        }

        return privateAddressList;
    }

}

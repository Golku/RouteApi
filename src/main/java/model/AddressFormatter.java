package model;

import model.pojos.Address;

public class AddressFormatter {

    /**
     * Formats the given address to a standard format of  "street, postCode city, country"
     */

    public void format(Address address){
        try {
            formatAddress(address);
            if(!address.getCountry().contains("Netherlands")){
                address.setValid(false);
            }

        } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            address.setValid(false);
        }
    }

    private void displayAddress(Address address){
        System.out.println("Address: " + address.getAddress());
        System.out.println("Street: " + address.getStreet());
        System.out.println("postcode: " + address.getPostCode());
        System.out.println("City: "+ address.getCity());
        System.out.println("country: "+ address.getCountry());
    }

    private void formatAddress(Address currentAddress) {

        String address = currentAddress.getAddress();

        currentAddress.setStreet(address.split(",")[0]);
        currentAddress.setCountry(address.split(",")[2].substring(1));

        if (address.split(",")[1].substring(1, 4).matches("[0-9]+")) {

            currentAddress.setPostCode(address.split(",")[1].substring(1, 8));
            currentAddress.setCity(address.split(",")[1].substring(9));

            String postCodeLettersHolder = currentAddress.getPostCode().substring(4, 7).replaceAll(" ", "");

            currentAddress.setPostCode(currentAddress.getPostCode().substring(0, 4));

            if (Character.isUpperCase(postCodeLettersHolder.charAt(0))) {

                if (Character.isUpperCase(postCodeLettersHolder.charAt(1))) {
                    currentAddress.setPostCode(currentAddress.getPostCode() + "" + postCodeLettersHolder);

                    if (address.split(",")[1].substring(1, 7).contains(currentAddress.getPostCode().replaceAll(" ", ""))) {
                        currentAddress.setCity(address.split(",")[1].substring(8));
                    }

                } else {
                    currentAddress.setCity(address.split(",")[1].substring(6));
                }

            }

            currentAddress.setAddress(
                    currentAddress.getStreet() + ", " +
                            currentAddress.getPostCode() + " " +
                            currentAddress.getCity() + ", " +
                            currentAddress.getCountry()
            );

        } else {
            currentAddress.setPostCode("");
            currentAddress.setCity(address.split(",")[1].substring(1));
        }

        //displayAddress(currentAddress);
    }
}

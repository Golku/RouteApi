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

    private void formatAddress(Address currentAddress) {

        String address = currentAddress.getAddress();

        int commasCount = 0;

        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) == ',') commasCount++;
        }

//            System.out.println("Commas count: "+commasCount);
//            System.out.println("");

        if (commasCount == 1) {

//                System.out.println("Address to be process 1: "+address);
//                System.out.println("");

            currentAddress.setStreet(address.split(",")[0]);
            currentAddress.setPostCode("");
            currentAddress.setCity(address.split(",")[1].substring(1));
            currentAddress.setCountry("");
        } else {

            if (commasCount == 3) {
                address = address.split(",")[1].substring(1) + "," +
                        address.split(",")[2] + "," +
                        address.split(",")[3];
            }

//                System.out.println("Address to be process 2: "+address);
//                System.out.println("");

            currentAddress.setStreet(address.split(",")[0]);
            currentAddress.setPostCode(address.split(",")[1].substring(1, 8));
            currentAddress.setCity(address.split(",")[1].substring(9));
            currentAddress.setCountry(address.split(",")[2].substring(1));

            if (currentAddress.getPostCode().substring(0, 4).matches("[0-9]+")) {

                String postCodeLettersHolder = currentAddress.getPostCode().substring(4, 7).replaceAll(" ", "");

                currentAddress.setPostCode(currentAddress.getPostCode().substring(0, 4));

                if (Character.isUpperCase(postCodeLettersHolder.charAt(0))) {

                    if (Character.isUpperCase(postCodeLettersHolder.charAt(1))) {
                        currentAddress.setPostCode(currentAddress.getPostCode() + " " + postCodeLettersHolder);

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

//                    System.out.println("2: " + getformattedAddress());
//                    System.out.println(postCodeLettersHolder);
//                    System.out.println(getPostCode());
//                    System.out.println(getCity());
            }
        }
    }
}

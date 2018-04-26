package model;

import model.pojos.FormattedAddress;

public class AddressFormatter {

    /**
     * Formats the given address to a standard format of  "street, postCode city, country"
     */

    public void orderAddress(FormattedAddress address) {
        try {
            tryToOrderAddress(address);
        } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            address.setInvalid(true);
        }
    }

    private void tryToOrderAddress(FormattedAddress formattedAddress) {

        String address = formattedAddress.getFormattedAddress();

        int commasCount = 0;

        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) == ',') commasCount++;
        }

//            System.out.println("Commas count: "+commasCount);
//            System.out.println("");

        if (commasCount == 1) {

//                System.out.println("Address to be process 1: "+address);
//                System.out.println("");

            formattedAddress.setStreet(address.split(",")[0]);
            formattedAddress.setPostCode("");
            formattedAddress.setCity(address.split(",")[1].substring(1));
            formattedAddress.setCountry("");
        } else {

            if (commasCount == 3) {
                address = address.split(",")[1].substring(1) + "," +
                        address.split(",")[2] + "," +
                        address.split(",")[3];
            }

//                System.out.println("Address to be process 2: "+address);
//                System.out.println("");

            formattedAddress.setStreet(address.split(",")[0]);
            formattedAddress.setPostCode(address.split(",")[1].substring(1, 8));
            formattedAddress.setCity(address.split(",")[1].substring(9));
            formattedAddress.setCountry(address.split(",")[2].substring(1));

            if (formattedAddress.getPostCode().substring(0, 4).matches("[0-9]+")) {

                String postCodeLettersHolder = formattedAddress.getPostCode().substring(4, 7).replaceAll(" ", "");

                formattedAddress.setPostCode(formattedAddress.getPostCode().substring(0, 4));

                if (Character.isUpperCase(postCodeLettersHolder.charAt(0))) {

                    if (Character.isUpperCase(postCodeLettersHolder.charAt(1))) {
                        formattedAddress.setPostCode(formattedAddress.getPostCode() + " " + postCodeLettersHolder);

                        if (address.split(",")[1].substring(1, 7).contains(formattedAddress.getPostCode().replaceAll(" ", ""))) {
                            formattedAddress.setCity(address.split(",")[1].substring(8));
                        }

                    } else {
                        formattedAddress.setCity(address.split(",")[1].substring(6));
                    }

                }

                formattedAddress.setFormattedAddress(
                        formattedAddress.getStreet() + ", " +
                                formattedAddress.getPostCode() + " " +
                                formattedAddress.getCity() + ", " +
                                formattedAddress.getCountry()
                );

//                    System.out.println("2: " + getformattedAddress());
//                    System.out.println(postCodeLettersHolder);
//                    System.out.println(getPostCode());
//                    System.out.println(getCity());
            }
        }
    }
}

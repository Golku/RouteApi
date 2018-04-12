package model;

import model.pojos.FormattedAddress;
import model.pojos.SingleDrive;
import model.pojos.UnorganizedRoute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RoutesOrganizer {

    private GoogleMapsApi googleMapsApi;

    private String origin;

    private List<SingleDrive> organizedRouteClosestAddress;

    private int packageDeliveryTime;
    private SimpleDateFormat sdf;
    private long date;
    private long deliveryTime;
    private long deliveryTimeSum;

    public RoutesOrganizer(GoogleMapsApi googleMapsApi) {

        this.googleMapsApi = googleMapsApi;

        sdf = new SimpleDateFormat("kk:mm");
        date = System.currentTimeMillis();

        origin = "";
        packageDeliveryTime = 120000;
        deliveryTimeSum = 0;
        deliveryTime = 0;
    }

    public List<SingleDrive> organizeRouteClosestAddress(UnorganizedRoute unorganizedRoute) {

        if(unorganizedRoute.getOrigin() == null || unorganizedRoute.getOrigin().isEmpty()){
            this.origin = "vrij-harnasch 21, den hoorn";
        }else{
            this.origin = unorganizedRoute.getOrigin();
        }

//        System.out.println("First origin: "+origin);

        this.organizedRouteClosestAddress = new ArrayList<>();

        List<FormattedAddress> privateAddressList = new ArrayList<>(unorganizedRoute.getPrivateAddressList());

        List<FormattedAddress> businessAddressList = new ArrayList<>(unorganizedRoute.getBusinessAddressList());

//        System.out.println("unorganizedRoute business list size: "+unorganizedRoute.getBusinessAddressList().size());
//        System.out.println("unorganizedRoute private list size: "+unorganizedRoute.getPrivateAddressList().size());
//        System.out.println("temp business list size: " + businessAddressList.size());
//        System.out.println("temp private list size: "+privateAddressList.size());
//        System.out.println("");

        if (businessAddressList.size() > 0) {
            try {
                getDriveInformation(businessAddressList);
            }catch (NullPointerException e){
//                e.printStackTrace();
                System.out.println("Null object at RouteOrganizer.java at block 61");
            }
        }

        if (privateAddressList.size() > 0){
            try {
                getDriveInformation(privateAddressList);
            }catch (NullPointerException e){
                System.out.println("Null object at RouteOrganizer.java at block 69");
            }
        }

//        System.out.println(unorganizedRoute.getBusinessAddressList().size());
//        System.out.println(unorganizedRoute.getPrivateAddressList().size());
//        System.out.println(businessAddressList.size());
//        System.out.println(privateAddressList.size());

        //This list can be returned empty if getDriveInformation() fails. FIX THIS!!!
        return organizedRouteClosestAddress;
    }

    private void getDriveInformation(List<FormattedAddress> addressList){

        Boolean organisingInProgress = true;

        String destination;

        long driveDuration;
        long shortestDriveDuration = 0;

        List<SingleDrive> DrivesList = new ArrayList<>();
        SingleDrive shortestDrive = new SingleDrive();

        while(organisingInProgress){

            for (FormattedAddress formattedAddress : addressList) {

                destination = formattedAddress.getFormattedAddress();

//                System.out.println("call Origin: "+origin);
//                System.out.println("call Destination: "+destination);
//                System.out.println("");

//                single drive might be returned as null. FiX THIS!!
                SingleDrive singleDrive = googleMapsApi.getDriveInformation(origin, destination);

//                System.out.println("formatted raw Origin: "+singleDrive.getOriginFormattedAddress().getRawAddress());
//                System.out.println("formatted Origin: "+singleDrive.getOriginFormattedAddress().getFormattedAddress());
//                System.out.println("formatted raw Destination: "+singleDrive.getDestinationFormattedAddress().getRawAddress());
//                System.out.println("formatted Destination: "+singleDrive.getDestinationFormattedAddress().getFormattedAddress());

                if(singleDrive != null){
                    singleDrive.getDestinationFormattedAddress().setLat(formattedAddress.getLat());
                    singleDrive.getDestinationFormattedAddress().setLng(formattedAddress.getLng());

                    if (formattedAddress.getIsBusiness()) {
//                    System.out.println("Destination is a business");
                        singleDrive.setDestinationIsABusiness(true);
                    }
                }




//                System.out.println("");

                DrivesList.add(singleDrive);
            }

            for(int i = 0; i< DrivesList.size(); i++){

                driveDuration = DrivesList.get(i).getDriveDurationInSeconds();

                if(i==0){
                    shortestDriveDuration = DrivesList.get(i).getDriveDurationInSeconds();
                }

                if(driveDuration <= shortestDriveDuration){
                    shortestDriveDuration = driveDuration;
                    shortestDrive = DrivesList.get(i);
                }

            }

            deliveryTime = date + (shortestDrive.getDriveDurationInSeconds()*1000) + packageDeliveryTime + deliveryTimeSum;
            String deliveryTimeString = sdf.format(deliveryTime);
            deliveryTimeSum = deliveryTimeSum +(shortestDrive.getDriveDurationInSeconds()*1000)+ packageDeliveryTime;

            shortestDrive.setDeliveryTimeInMillis(deliveryTime);
            shortestDrive.setDeliveryTimeHumanReadable(deliveryTimeString);

            organizedRouteClosestAddress.add(shortestDrive);

//            System.out.println("shortDrive destination: " + shortestDrive.getDestinationFormattedAddress().getFormattedAddress());

            origin = shortestDrive.getDestinationFormattedAddress().getFormattedAddress();

//            System.out.println("Origin: " + origin);

            DrivesList.clear();

            for(int i=0; i<addressList.size(); i++){

//                System.out.println("Validated: " + addressList.get(i).getFormattedAddress());
//                System.out.println("Short: " + shortestDrive.getDestinationFormattedAddress().getFormattedAddress());
//                System.out.println("");

                //won't work if you have the same street name and number twice in different city. FIX THIS!
                if(addressList.get(i).getFormattedAddress().contains(shortestDrive.getDestinationFormattedAddress().getFormattedAddress())){
//                    System.out.println("Contains");
//                    System.out.println(addressList.get(i).getFormattedAddress());
//                    System.out.println(shortestDrive.getDestinationFormattedAddress().getFormattedAddress());
//                    System.out.println("");
                    addressList.remove(i);
                }else{
//                    System.out.println("No contains");
//                    System.out.println(addressesList.get(i));
//                    System.out.println(shortestDrive.getDestinationCompleteAddress());
                }

            }

            //System.out.println("Size: "+addressList.size());

            if(addressList.size() == 0){
                organisingInProgress = false;
            }

        }

    }


}

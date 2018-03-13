package model;

import model.pojos.FormattedAddress;
import model.pojos.SingleDrive;
import model.pojos.UnOrganizedRoute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RoutesOrganizer {

    private GoogleMapsApi googleMapsApi;

    public String origin;

    private List<SingleDrive> organizedRouteClosestAddress = new ArrayList<>();

    private int packageDeliveryTime;
    private SimpleDateFormat sdf;
    private long date;
    private long deliveryTime;
    private long deliveryTimeSum;

    public RoutesOrganizer(GoogleMapsApi googleMapsApi) {

        this.googleMapsApi = googleMapsApi;

        sdf = new SimpleDateFormat("kk:mm");
        date = System.currentTimeMillis();

        packageDeliveryTime = 120000;
        deliveryTimeSum = 0;
        deliveryTime = 0;

    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<SingleDrive> organizeRouteClosestAddress(UnOrganizedRoute unorganizedRoute) {

        List<FormattedAddress> businessAddressList = new ArrayList<>();
        List<FormattedAddress> privateAddressList = new ArrayList<>();

        for (int i = 0; i < unorganizedRoute.getPrivateAddressList().size(); i++) {
            privateAddressList.add(unorganizedRoute.getPrivateAddressList().get(i));
        }

        for (int i = 0; i < unorganizedRoute.getBusinessAddressList().size(); i++) {
            businessAddressList.add(unorganizedRoute.getBusinessAddressList().get(i));
        }

//        System.out.println(unorganizedRoute.getBusinessAddressList().size());
//        System.out.println(unorganizedRoute.getPrivateAddressList().size());
//        System.out.println(businessAddressList.size());
//        System.out.println(privateAddressList.size());

        if (businessAddressList.size() > 0) {
            getDriveInformation(businessAddressList);
        }

        if (privateAddressList.size() > 0){
            getDriveInformation(privateAddressList);
        }

//        System.out.println(unorganizedRoute.getBusinessAddressList().size());
//        System.out.println(unorganizedRoute.getPrivateAddressList().size());
//        System.out.println(businessAddressList.size());
//        System.out.println(privateAddressList.size());

        return organizedRouteClosestAddress;

    }

    private void getDriveInformation(List<FormattedAddress> addressList){

        Boolean organisingInProgress = true;

        String destination;

        long driveDuration;
        long shortestDriveDuration = 0;

        List<SingleDrive> singleDriveList = new ArrayList<>();
        SingleDrive shortestDrive = new SingleDrive();

        //System.out.println("Origin: "+origin);

        while(organisingInProgress){

            for(int i=0; i< addressList.size(); i++){

                destination = addressList.get(i).getFormattedAddress();

//                System.out.println("call Origin: "+origin);
//                System.out.println("call Destination: "+destination);
//                System.out.println("");

                SingleDrive singleDrive = googleMapsApi.getDriveInformation(origin, destination);

//                System.out.println("formatted raw Origin: "+singleDrive.getOriginFormattedAddress().getRawAddress());
//                System.out.println("formatted Origin: "+singleDrive.getOriginFormattedAddress().getFormattedAddress());
//                System.out.println("formatted raw Destination: "+singleDrive.getDestinationFormattedAddress().getRawAddress());
//                System.out.println("formatted Destination: "+singleDrive.getDestinationFormattedAddress().getFormattedAddress());
//                System.out.println("");

                if(addressList.get(i).getIsBusiness()){
                    singleDrive.setDestinationIsABusiness(true);
                }

                singleDriveList.add(singleDrive);
            }

            for(int i=0; i<singleDriveList.size(); i++){

                driveDuration = singleDriveList.get(i).getDriveDurationInSeconds();

                if(i==0){
                    shortestDriveDuration = singleDriveList.get(i).getDriveDurationInSeconds();
                }

                if(driveDuration <= shortestDriveDuration){
                    shortestDriveDuration = driveDuration;
                    shortestDrive = singleDriveList.get(i);
                }

            }

            deliveryTime = date + (shortestDrive.getDriveDurationInSeconds()*1000) + packageDeliveryTime + deliveryTimeSum;
            String deliveryTimeString = sdf.format(deliveryTime);
            deliveryTimeSum = deliveryTimeSum +(shortestDrive.getDriveDurationInSeconds()*1000)+ packageDeliveryTime;

            shortestDrive.setDeliveryTimeInMillis(deliveryTime);
            shortestDrive.setDeliveryTimeHumanReadable(deliveryTimeString);

            organizedRouteClosestAddress.add(shortestDrive);
            origin = shortestDrive.getDestinationFormattedAddress().getFormattedAddress();

            singleDriveList.clear();

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

package model;

import com.google.gson.JsonSyntaxException;
import model.pojos.Address;
import model.pojos.DbAddressInfo;
import model.pojos.DbDriveInfo;
import model.pojos.Drive;
import retrofit2.Call;

import java.io.IOException;

public class DbManager {

    private DatabaseService databaseService;
    private long date;

    public DbManager(DatabaseService databaseService) {
        this.databaseService = databaseService;
        date = System.currentTimeMillis();
    }

    public void getAddressInfo(Address address) {

        String street = address.getStreet();
        String postCode = address.getPostCode();
        String city = address.getCity();

        Call<DbAddressInfo> call = databaseService.getAddressInfo(
                street,
                postCode,
                city
        );

        DbAddressInfo dbAddressInfo = null;

        try {
            dbAddressInfo = call.execute().body();
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Database request failed for: " + street + ", " + postCode + " " + city);
        }

        if (dbAddressInfo != null) {
            if (dbAddressInfo.getBusiness() == 1) {
                address.setBusiness(true);
                address.setOpeningTime(dbAddressInfo.getOpeningTime());
                address.setClosingTime(dbAddressInfo.getClosingTime());
            }else{
                address.setBusiness(false);
            }
        }
    }


    public void getDriveInfo(Drive drive) {

        String origin = drive.getOriginAddress().getAddress();
        String destination = drive.getDestinationAddress().getAddress();

        Call<DbDriveInfo> call = databaseService.getDriveInfo(origin, destination);

        DbDriveInfo dbDriveInfo = null;

        try {
            dbDriveInfo = call.execute().body();
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Database request failed for: " + origin + " - " + destination);
        }

        if(dbDriveInfo != null){
            if(dbDriveInfo.isInfoAvailable()){

                long refreshDate = dbDriveInfo.getRefreshDate();

                if(date < refreshDate){

                    drive.getOriginAddress().setAddress(dbDriveInfo.getOriginAddress());
                    drive.getDestinationAddress().setAddress(dbDriveInfo.getDestinationAddress());
                    drive.setDriveDistanceInMeters(dbDriveInfo.getDistanceInMeters());
                    drive.setDriveDistanceHumanReadable(dbDriveInfo.getDistanceHumanReadable());
                    drive.setDriveDurationInSeconds(dbDriveInfo.getDurationInSeconds());
                    drive.setDriveDurationHumanReadable(dbDriveInfo.getDurationHumanReadable());

                    drive.setValid(true);
                    //System.out.println("From db");
                }else{
                    System.out.println("Date expired, date: " + date + " refresh date: " + refreshDate);
                }
            }else{
                System.out.println("Info not available");
            }
        }else{
            System.out.println("dbDriveInfo is null");
        }
    }

    public void addDriveInfo(Drive drive) {

        Call<Void> call = databaseService.addDriveInfo(
                drive.getOriginAddress().getAddress(),
                drive.getDestinationAddress().getAddress(),
                drive.getDriveDistanceInMeters(),
                drive.getDriveDistanceHumanReadable(),
                drive.getDriveDurationInSeconds(),
                drive.getDriveDurationHumanReadable(),
                date + 432000000 //current date + 5 days in milliseconds
        );

        try {
            call.execute();
            System.out.println("Drive added to db");
        } catch (IOException e) {
            System.out.println("Failed to add drive info to db: " + e);
        }
    }
}

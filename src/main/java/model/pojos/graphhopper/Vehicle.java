package model.pojos.graphhopper;

public class Vehicle {

    public String vehicle_id;
    public StartAddress start_address;
    public boolean return_to_depot;

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public StartAddress getStart_address() {
        return start_address;
    }

    public void setStart_address(StartAddress start_address) {
        this.start_address = start_address;
    }

    public boolean isReturn_to_depot() {
        return return_to_depot;
    }

    public void setReturn_to_depot(boolean return_to_depot) {
        this.return_to_depot = return_to_depot;
    }
}

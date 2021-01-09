package model.pojos.graphhopper;

public class VehicleWithEndAddress {

    public String vehicle_id;
    public StartAddress start_address;
    public EndAddress end_address;

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

    public EndAddress getEnd_address() {
        return end_address;
    }

    public void setEnd_address(EndAddress end_address) {
        this.end_address = end_address;
    }
}

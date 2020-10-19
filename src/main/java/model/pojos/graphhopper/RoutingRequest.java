package model.pojos.graphhopper;

import java.util.List;

public class RoutingRequest {

    public List<Vehicle> vehicles;
    public List<Service> services;

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}

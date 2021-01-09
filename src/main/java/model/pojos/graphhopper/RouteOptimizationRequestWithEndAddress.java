package model.pojos.graphhopper;

import java.util.List;

public class RouteOptimizationRequestWithEndAddress {

    public List<VehicleWithEndAddress> vehicles;
    public List<Service> services;

    public List<VehicleWithEndAddress> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleWithEndAddress> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}

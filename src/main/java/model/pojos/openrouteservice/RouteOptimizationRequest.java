package model.pojos.openrouteservice;

import java.util.List;

public class RouteOptimizationRequest {

    public List<Job> jobs;
    public List<Vehicle> vehicles;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}

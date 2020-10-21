package model.pojos.graphhopper;

import java.util.List;

public class Route {

    public String vehicle_id;
    public int distance;
    public int transport_time;
    public int completion_time;
    public int waiting_time;
    public int service_duration;
    public int preparation_time;
    public List<Point> points;
    public List<Activity> activities;

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTransport_time() {
        return transport_time;
    }

    public void setTransport_time(int transport_time) {
        this.transport_time = transport_time;
    }

    public int getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(int completion_time) {
        this.completion_time = completion_time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getService_duration() {
        return service_duration;
    }

    public void setService_duration(int service_duration) {
        this.service_duration = service_duration;
    }

    public int getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(int preparation_time) {
        this.preparation_time = preparation_time;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}

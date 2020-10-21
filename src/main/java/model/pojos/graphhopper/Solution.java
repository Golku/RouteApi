package model.pojos.graphhopper;

import java.util.List;

public class Solution {

    public int costs;
    public int distance;
    public int time;
    public int transport_time;
    public int completion_time;
    public int max_operation_time;
    public int waiting_time;
    public int service_duration;
    public int preparation_time;
    public int no_vehicles;
    public int no_unassigned;
    public List<Route> routes;
    public Unassigned unassigned;

    public int getCosts() {
        return costs;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public int getMax_operation_time() {
        return max_operation_time;
    }

    public void setMax_operation_time(int max_operation_time) {
        this.max_operation_time = max_operation_time;
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

    public int getNo_vehicles() {
        return no_vehicles;
    }

    public void setNo_vehicles(int no_vehicles) {
        this.no_vehicles = no_vehicles;
    }

    public int getNo_unassigned() {
        return no_unassigned;
    }

    public void setNo_unassigned(int no_unassigned) {
        this.no_unassigned = no_unassigned;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Unassigned getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(Unassigned unassigned) {
        this.unassigned = unassigned;
    }
}

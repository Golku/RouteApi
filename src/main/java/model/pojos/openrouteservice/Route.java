package model.pojos.openrouteservice;

import java.util.List;

public class Route {

    public int vehicle;
    public int cost;
    public List<Integer> delivery;
    public List<Integer> amount;
    public List<Integer> pickup;
    public int service;
    public int duration;
    public int waiting_time;
    public List<Step> steps;

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Integer> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<Integer> delivery) {
        this.delivery = delivery;
    }

    public List<Integer> getAmount() {
        return amount;
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public List<Integer> getPickup() {
        return pickup;
    }

    public void setPickup(List<Integer> pickup) {
        this.pickup = pickup;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}

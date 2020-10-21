package model.pojos.graphhopper;

import java.util.List;

public class Activity {

    public String type;
    public String location_id;
    public Address address;
    public int end_time;
    public Object end_date_time;
    public int distance;
    public int driving_time;
    public int preparation_time;
    public int waiting_time;
    public List<Integer> load_after;
    public String id;
    public int arr_time;
    public Object arr_date_time;
    public List<Integer> load_before;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public Object getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(Object end_date_time) {
        this.end_date_time = end_date_time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDriving_time() {
        return driving_time;
    }

    public void setDriving_time(int driving_time) {
        this.driving_time = driving_time;
    }

    public int getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(int preparation_time) {
        this.preparation_time = preparation_time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public List<Integer> getLoad_after() {
        return load_after;
    }

    public void setLoad_after(List<Integer> load_after) {
        this.load_after = load_after;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getArr_time() {
        return arr_time;
    }

    public void setArr_time(int arr_time) {
        this.arr_time = arr_time;
    }

    public Object getArr_date_time() {
        return arr_date_time;
    }

    public void setArr_date_time(Object arr_date_time) {
        this.arr_date_time = arr_date_time;
    }

    public List<Integer> getLoad_before() {
        return load_before;
    }

    public void setLoad_before(List<Integer> load_before) {
        this.load_before = load_before;
    }
}

package model.pojos.openrouteservice;

import java.util.List;

public class Vehicle {

    public int id;
    public String profile;
    public List<Double> start;
    public List<Double> end;
    public List<Integer> capacity;
    public List<Integer> skills;
    public List<Integer> time_window;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public List<Double> getStart() {
        return start;
    }

    public void setStart(List<Double> start) {
        this.start = start;
    }

    public List<Double> getEnd() {
        return end;
    }

    public void setEnd(List<Double> end) {
        this.end = end;
    }

    public List<Integer> getCapacity() {
        return capacity;
    }

    public void setCapacity(List<Integer> capacity) {
        this.capacity = capacity;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    public List<Integer> getTime_window() {
        return time_window;
    }

    public void setTime_window(List<Integer> time_window) {
        this.time_window = time_window;
    }
}

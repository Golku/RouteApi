package model.pojos.openrouteservice;

import java.util.List;

public class Job {

    public int id;
    public int service;
    public List<Double> location;
    public List<Integer> skills;
    public List<List<Integer>> time_windows;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    public List<List<Integer>> getTime_windows() {
        return time_windows;
    }

    public void setTime_windows(List<List<Integer>> time_windows) {
        this.time_windows = time_windows;
    }
}

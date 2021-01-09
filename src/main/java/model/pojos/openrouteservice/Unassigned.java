package model.pojos.openrouteservice;

import java.util.List;

public class Unassigned {

    public int id;
    public List<Double> location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }
}

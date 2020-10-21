package model.pojos.graphhopper;

import java.util.List;

public class Unassigned {

    public List<Object> services;
    public List<Object> shipments;
    public List<Object> breaks;
    public List<Object> details;

    public List<Object> getServices() {
        return services;
    }

    public void setServices(List<Object> services) {
        this.services = services;
    }

    public List<Object> getShipments() {
        return shipments;
    }

    public void setShipments(List<Object> shipments) {
        this.shipments = shipments;
    }

    public List<Object> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<Object> breaks) {
        this.breaks = breaks;
    }

    public List<Object> getDetails() {
        return details;
    }

    public void setDetails(List<Object> details) {
        this.details = details;
    }
}

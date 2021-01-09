package model.pojos.openrouteservice;

import java.util.List;

public class GeocodingResults {

    public List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}

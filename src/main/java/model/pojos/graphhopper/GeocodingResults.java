package model.pojos.graphhopper;

import model.pojos.graphhopper.Hit;

import java.util.List;

public class GeocodingResults {

    public List<Hit> hits;
    public String locale;

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}

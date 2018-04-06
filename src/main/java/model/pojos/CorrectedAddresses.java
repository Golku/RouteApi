package model.pojos;

import java.util.List;

public class CorrectedAddresses {

    private String routeCode;
    private List<String> correctedAddressesList;

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public List<String> getCorrectedAddressesList() {
        return correctedAddressesList;
    }

    public void setCorrectedAddressesList(List<String> correctedAddressesList) {
        this.correctedAddressesList = correctedAddressesList;
    }
}

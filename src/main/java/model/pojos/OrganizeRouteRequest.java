package model.pojos;

import java.util.List;

public class OrganizeRouteRequest {

    private String username;
    private String startLocation;
    private String endLocation;
    private List<String> routeList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public List<String> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<String> routeList) {
        this.routeList = routeList;
    }
}

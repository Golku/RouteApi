package model.pojos;

import java.util.List;

public class CorrectedAddresses {

    private String username;
    private List<String> correctedAddressesList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getCorrectedAddressesList() {
        return correctedAddressesList;
    }

    public void setCorrectedAddressesList(List<String> correctedAddressesList) {
        this.correctedAddressesList = correctedAddressesList;
    }
}

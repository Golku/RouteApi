package model.pojos;

import com.google.maps.model.AutocompleteStructuredFormatting;

public class Prediction {

    private String description;
    private String placeId;
    private AutocompleteStructuredFormatting structuredFormatting;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public AutocompleteStructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    public void setStructuredFormatting(AutocompleteStructuredFormatting structuredFormatting) {
        this.structuredFormatting = structuredFormatting;
    }
}

package com.RouteApi;

import controller.AddressController;
import model.pojos.AutocompletePrediction;
import model.pojos.openrouteservice.AutocompleteRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/autocomplete")
public class GetAutocomplete {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<AutocompletePrediction> autocompleteRequest(AutocompleteRequest request) {
        return new AddressController().getAutocomplete(request);
    }
}

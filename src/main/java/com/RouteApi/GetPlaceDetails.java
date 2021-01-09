package com.RouteApi;

import controller.AddressController;
import model.pojos.Address;
import model.pojos.AddressRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/placedetails")
public class GetPlaceDetails {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Address placeDetailsRequest(AddressRequest request) {
        return new AddressController().getPlaceDetails(request);
    }
}

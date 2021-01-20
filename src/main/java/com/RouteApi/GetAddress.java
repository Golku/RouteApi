package com.RouteApi;

import controller.AddressController;
import model.pojos.Address;
import model.pojos.AddressRequest;
import model.pojos.AddressResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/address")
public class GetAddress {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AddressResponse addressRequest(AddressRequest request) {
        return new AddressController().getAddress(request);
    }
}

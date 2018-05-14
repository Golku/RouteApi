package com.RouteApi;

import controller.AddressController;
import model.pojos.Address;
import model.pojos.AddressChangeRequest;
import model.pojos.AddressRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/changeaddress")
public class ChangeAddress {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Address addressChangeRequest(AddressChangeRequest request) {
        return new AddressController().changeAddress(request);
    }
}

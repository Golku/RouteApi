package com.RouteApi;

import controller.AddressController;
import model.pojos.RemoveAddressRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/removeaddress")
public class RemoveAddress {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeAddress(RemoveAddressRequest request) {
        AddressController controller = new AddressController();
        controller.removeAddress(request);
        return Response.accepted().build();
    }
}
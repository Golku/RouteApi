package com.RouteApi;

import controller.GetInvalidAddressesController;
import model.pojos.ApiResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/getinvalidaddresses")
public class GetInvalidAddresses {

    GetInvalidAddressesController controller = new GetInvalidAddressesController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getInvalidAddresses() {
        return controller.checkForInvalidAddresses("");
    }

    @GET
    @Path("/{routeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getInvalidAddresses(@PathParam("routeCode") String routeCode) {
        return controller.checkForInvalidAddresses(routeCode);
    }

}

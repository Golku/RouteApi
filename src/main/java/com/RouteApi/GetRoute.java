package com.RouteApi;

import controller.Controller;
import model.pojos.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/getroute")
public class GetRoute {

    private Controller controller = new Controller();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse retrieveSingleOrganizedRoute() {
        return controller.getRoute("");
    }

    @GET
    @Path("/{routeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse retrieveSingleOrganizedRoute(@PathParam("routeCode") String routeCode) {
        return controller.getRoute(routeCode);
    }

}


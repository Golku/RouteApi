package com.RouteApi;

import controller.Controller;
import model.pojos.IncomingRoute;
import model.pojos.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/getroute")
public class GetRoute {

    private Controller controller = new Controller();

    @GET
    @Path("/{routeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSingleOrganizedRoute(@PathParam("routeCode") String routeCode) {
        return controller.getRoute(routeCode);
    }

}


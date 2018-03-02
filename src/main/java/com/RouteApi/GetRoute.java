package com.RouteApi;

import controller.GetRouteController;
import controller.SubmitRouteController;
import model.pojos.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/getroute")
public class GetRoute {

    private GetRouteController controller = new GetRouteController();

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


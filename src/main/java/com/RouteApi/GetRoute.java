package com.RouteApi;

import controller.RouteController;
import model.pojos.Route;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getroute")
public class GetRoute {

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Route getRoute(@PathParam("username") String username) {
        RouteController controller = new RouteController();
        return controller.fetchRoute(username);
    }

}

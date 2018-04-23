package com.RouteApi;

import controller.RouteController;
import model.pojos.IncomingRoute;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/submitroute")
public class SubmitRoute {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void submitRouteForOrganizing(final IncomingRoute route) {
        RouteController controller = new RouteController();
        controller.createRoute(route);
    }
}

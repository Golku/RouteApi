package com.RouteApi;

import controller.RouteController;
import model.pojos.IncomingRoute;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/route")
public class SubmitRoute {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitRoute(IncomingRoute route) {
        RouteController controller = new RouteController();
        controller.createRoute(route);
        return Response.accepted().build();
    }
}

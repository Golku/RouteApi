package com.RouteApi;

import controller.RouteController;
import model.pojos.OrganizeRouteRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/route")
public class SubmitRoute {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitRoute(OrganizeRouteRequest route) {
        RouteController controller = new RouteController();
        controller.organizedRoute(route);
        return Response.accepted().build();
    }
}

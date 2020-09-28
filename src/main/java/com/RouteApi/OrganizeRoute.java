package com.RouteApi;

import controller.RouteController;
import model.pojos.OrganizeRouteRequest;
import model.pojos.RouteResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/route")
public class OrganizeRoute {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RouteResponse submitRoute(OrganizeRouteRequest route) {
        RouteController controller = new RouteController();
        return controller.organizedRoute(route);
    }
}

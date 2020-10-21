package com.RouteApi;

import controller.RouteController;
import model.pojos.OrganizeRouteRequest;
import model.pojos.OptimizedRoute;
import model.pojos.graphhopper.RouteOptimizationResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/route")
public class OrganizeRoute {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OptimizedRoute submitRoute(OrganizeRouteRequest route) {
        RouteController controller = new RouteController();
        return controller.organizedRoute(route);
    }
}

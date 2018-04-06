package com.RouteApi;

import controller.GetRouteController;
import model.pojos.RouteResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/getroute")
public class GetRoute {

    GetRouteController controller = new GetRouteController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RouteResponse getRouteState() {
        return controller.checkForRouteState("");
    }

    @GET
    @Path("/{routeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public RouteResponse getInvalidAddresses(@PathParam("routeCode") String routeCode) {
        return controller.checkForRouteState(routeCode);
    }

}

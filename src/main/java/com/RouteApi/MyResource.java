package com.RouteApi;

import model.pojos.IncomingRoute;
import model.pojos.SingleOrganizedRoute;
import model.pojos.SingleUnOrganizedRoute;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/myresource")
public class MyResource {

    private Main main = new Main();

    @GET
    @Path("/{routeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public SingleUnOrganizedRoute getUnorganizedRoute(@PathParam("routeCode") String routeCode) {
        return main.getUnOrganizedRoute(routeCode);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SingleOrganizedRoute getRoute(IncomingRoute route) {

        System.out.println("RouteCode: " + route.getRouteCode());

        for(int i=0; i<route.getAddressList().size(); i++){
            System.out.println("Address "+String.valueOf(i)+" "+route.getAddressList().get(i));
        }

        System.out.println("");

        return main.organizeRoute(route);
    }

}


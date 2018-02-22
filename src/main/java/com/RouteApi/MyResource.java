package com.RouteApi;

import controller.Controller;
import model.pojos.IncomingRoute;
import model.pojos.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/myresource")
public class MyResource {

    private Controller controller = new Controller();

    @GET
    @Path("/{routeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSingleOrganizedRoute(@PathParam("routeCode") String routeCode) {
        return controller.getRoute(routeCode);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response submitRouteForOrganizing(final IncomingRoute route) {

        final Response response = new Response();

        new Thread(new Runnable() {
            @Override
            public void run() {
                beginRouteOrganizing();
            }

            private void beginRouteOrganizing() {
                response.setOrganizingInProgress(true);
                controller.calculateRoute(route);
                //find a way to stop the thread after it's done
                //organizing the route. Doing this will prevent memory leak.
            }
        }).start();

//        System.out.println("RouteCode: " + route.getRouteCode());
//        System.out.println("RouteOrigin: " + route.getOrigin());
//
//        for (int i = 0; i < route.getAddressList().size(); i++) {
//            System.out.println("Address " + String.valueOf(i) + " " + route.getAddressList().get(i));
//        }

        return response;
    }

}


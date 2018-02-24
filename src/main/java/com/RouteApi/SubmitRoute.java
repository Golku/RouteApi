package com.RouteApi;

import controller.Controller;
import model.pojos.IncomingRoute;
import model.pojos.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/submitroute")
public class SubmitRoute {

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
                Controller controller = new Controller();
                controller.calculateRoute(route);
                //find a way to stop the thread after it's done
                //organizing the route. Doing this will prevent memory leak.
            }
        }).start();

        response.setOrganizingInProgress(true);

        return response;
    }

}

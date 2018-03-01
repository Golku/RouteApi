package com.RouteApi;

import controller.Controller;
import model.pojos.IncomingRoute;
import model.pojos.ApiResponse;

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
    public ApiResponse submitRouteForOrganizing(final IncomingRoute route) {

        ApiResponse apiResponse = new ApiResponse();

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

        apiResponse.setRouteIsNull(false);
        apiResponse.setOrganizingInProgress(true);

        return apiResponse;
    }

}

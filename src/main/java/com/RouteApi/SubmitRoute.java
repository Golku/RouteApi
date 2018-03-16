package com.RouteApi;

import controller.SubmitRouteController;
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
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        beginRouteOrganizing();
                        Thread.currentThread().interrupt();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("thread stopped");
                }
            }

            private void beginRouteOrganizing() {
                SubmitRouteController submitRouteController = new SubmitRouteController();
                submitRouteController.calculateRoute(route);
                System.out.println("Done calculating the route");
            }

        }).start();

        apiResponse.setRequestType("submit route");
        apiResponse.setRouteState(1);

        return apiResponse;
    }

}

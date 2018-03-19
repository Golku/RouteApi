package com.RouteApi;

import controller.Controller;
import model.pojos.ApiResponse;
import model.pojos.UnOrganizedRoute;

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
    public ApiResponse submitRouteForOrganizing(final UnOrganizedRoute route) {

        ApiResponse apiResponse = new ApiResponse();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        processRoute();
                        Thread.currentThread().interrupt();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
//                    System.out.println("thread stopped");
                }
            }

            private void processRoute() {
                Controller controller = new Controller();
                controller.createRoute(route);
//                System.out.println("Done calculating the route");
            }

        }).start();

        apiResponse.setRequestType("submit route");
        apiResponse.setRouteState(1);

        return apiResponse;
    }

}

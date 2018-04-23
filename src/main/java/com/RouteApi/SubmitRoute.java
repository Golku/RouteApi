package com.RouteApi;

import controller.ContainerController;
import model.pojos.IncomingRoute;
import model.pojos.RouteResponse;

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
    public RouteResponse submitRouteForOrganizing(final IncomingRoute route) {

        RouteResponse routeResponse = new RouteResponse();

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
                ContainerController containerController = new ContainerController();
                containerController.createRoute(route);
//                System.out.println("Done calculating the route");
            }

        }).start();

        routeResponse.setRouteState(2);

        return routeResponse;
    }

}

package com.RouteApi;

import controller.Controller;
import model.pojos.IncomingRoute;
import model.pojos.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/invalidaddressessubmition")
public class InvalidAddressesSubmition {

    private Controller controller = new Controller();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse submitRouteForOrganizing(final IncomingRoute route) {

        final ApiResponse apiResponse = new ApiResponse();

        new Thread(new Runnable() {
            @Override
            public void run() {
                beginRouteOrganizing();
            }

            private void beginRouteOrganizing() {
                apiResponse.setOrganizingInProgress(true);
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

        return apiResponse;
    }

}

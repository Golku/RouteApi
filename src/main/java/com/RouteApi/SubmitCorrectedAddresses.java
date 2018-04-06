package com.RouteApi;

import controller.Controller;
import model.pojos.RouteResponse;
import model.pojos.CorrectedAddresses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/correctedaddressessubmition")
public class SubmitCorrectedAddresses {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RouteResponse submitCorrectedAddresses(final CorrectedAddresses correctedAddresses) {

        RouteResponse routeResponse = new RouteResponse();

        new Thread(new Runnable() {
            @Override
            public void run() {
                beginCorrectedAddressesCheck();
            }

            private void beginCorrectedAddressesCheck() {
                Controller controller = new Controller();
                controller.correctedAddresses(correctedAddresses);
                //find a way to stop the thread after it's done
                //organizing the route. Doing this will prevent memory leak.
            }
        }).start();

        routeResponse.setRouteState(2);

        return routeResponse;
    }

}

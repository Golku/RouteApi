package com.RouteApi;

import controller.Controller;
import model.pojos.ApiResponse;
import model.pojos.CorrectedAddresses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/correctedaddressessubmition")
public class SubmitCorrectedAddresses {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse submitCorrectedAddresses(final CorrectedAddresses correctedAddresses) {

        ApiResponse apiResponse = new ApiResponse();

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

        apiResponse.setRouteState(2);

        return apiResponse;
    }

}

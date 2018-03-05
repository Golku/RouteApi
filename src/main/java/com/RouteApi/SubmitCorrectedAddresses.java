package com.RouteApi;

import controller.SubmitRouteController;
import model.pojos.ApiResponse;
import model.pojos.CorrectedAddresses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/invalidaddressessubmition")
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
                SubmitRouteController controller = new SubmitRouteController();
                controller.checkSubmittedAddresses(correctedAddresses);
                //find a way to stop the thread after it's done
                //organizing the route. Doing this will prevent memory leak.
            }
        }).start();

        apiResponse.setRequestType("correct addresses");
        apiResponse.setRouteIsNull(false);
        apiResponse.setAddressValidatingInProgress(true);

        return apiResponse;
    }

}

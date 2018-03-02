package com.RouteApi;

import controller.SubmitCorrectedAddressesController;
import controller.SubmitRouteController;
import model.pojos.ApiResponse;
import model.pojos.CorrectedAddresses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Map;

@Path("/invalidaddressessubmition")
public class SubmitCorrectedAddresses {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse submitRouteForOrganizing(final CorrectedAddresses correctedAddresses) {

        final ApiResponse apiResponse = new ApiResponse();

        new Thread(new Runnable() {
            @Override
            public void run() {
                beginRouteOrganizing();
            }

            private void beginRouteOrganizing() {
                SubmitCorrectedAddressesController controller = new SubmitCorrectedAddressesController();
                controller.checkSubmittedAddresses(correctedAddresses);
                //find a way to stop the thread after it's done
                //organizing the route. Doing this will prevent memory leak.
            }
        }).start();

        //This wont work.
        apiResponse.setOrganizingInProgress(true);

        return apiResponse;
    }

}

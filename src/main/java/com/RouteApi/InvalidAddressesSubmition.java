package com.RouteApi;

import controller.Controller;
import model.pojos.IncomingRoute;
import model.pojos.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/invalidaddressessubmition")
public class InvalidAddressesSubmition {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse submitRouteForOrganizing(final ArrayList<String> correctedAddresses) {

        final ApiResponse apiResponse = new ApiResponse();

        new Thread(new Runnable() {
            @Override
            public void run() {
                beginRouteOrganizing();
            }

            private void beginRouteOrganizing() {
                Controller controller = new Controller();
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

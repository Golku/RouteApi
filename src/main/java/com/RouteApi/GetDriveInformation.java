package com.RouteApi;

import controller.Controller;
import model.pojos.ApiResponse;
import model.pojos.SingleDriveRequest;
import model.pojos.UnOrganizedRoute;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getdriveinformation")
public class GetDriveInformation {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse submitRouteForOrganizing(final SingleDriveRequest request) {
        ApiResponse apiResponse = new ApiResponse();

        Controller controller = new Controller();

        apiResponse.setSingleDrive(controller.getDriveInformation(request));

        return apiResponse;
    }

}

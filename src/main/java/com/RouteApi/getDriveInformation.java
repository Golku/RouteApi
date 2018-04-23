package com.RouteApi;

import controller.ContainerController;
import model.pojos.SingleDriveResponse;
import model.pojos.SingleDriveRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getdriveinformation")
public class getDriveInformation {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SingleDriveResponse getTravelInformation(final SingleDriveRequest request) {
        SingleDriveResponse singleDriveResponse = new SingleDriveResponse();

        ContainerController containerController = new ContainerController();

        singleDriveResponse.setSingleDrive(containerController.getDriveInformation(request));

        return singleDriveResponse;
    }

}

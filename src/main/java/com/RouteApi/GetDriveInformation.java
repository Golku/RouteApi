package com.RouteApi;

import controller.DriveInfoController;
import model.pojos.Drive;
import model.pojos.DriveRequest;

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
    public Drive getTravelInformation(DriveRequest request) {
        DriveInfoController controller = new DriveInfoController();
        return controller.getDriveInfo(request);
    }
}

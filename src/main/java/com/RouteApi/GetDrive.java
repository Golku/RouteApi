package com.RouteApi;

import controller.DriveInfoController;
import model.pojos.Drive;
import model.pojos.DriveRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/driverequest")
public class GetDrive {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Drive driveRequest(DriveRequest request) {
        return new DriveInfoController().getDriveInfo(request);
    }

}

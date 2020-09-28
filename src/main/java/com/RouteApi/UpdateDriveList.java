package com.RouteApi;

import controller.DriveInfoController;
import model.pojos.UpdateDriveListRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/updatedrivelist")
public class UpdateDriveList {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDriveList(UpdateDriveListRequest request) {
        DriveInfoController controller = new DriveInfoController();
        controller.updateDriveList(request);
        return Response.accepted().build();
    }

}

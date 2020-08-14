package com.RouteApi;

import controller.AddressController;
import model.pojos.UpdatePackageCountRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/packageCount")
public class UpdatePackageCount {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeAddress(UpdatePackageCountRequest request) {
        AddressController controller = new AddressController();
        controller.updatePackageCount(request);
        return Response.accepted().build();
    }
}

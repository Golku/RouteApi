package com.RouteApi;

import controller.RouteController;
import model.pojos.CorrectedAddresses;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/correctedaddressessubmition")
public class SubmitCorrectedAddresses {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void submitCorrectedAddresses(CorrectedAddresses correctedAddresses) {
        RouteController controller = new RouteController();
        controller.correctedAddresses(correctedAddresses);
    }
}

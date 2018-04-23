package com.RouteApi;

import controller.Controller;
import model.pojos.Container;
import model.pojos.RouteResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/getcontainer")
public class GetContainer {

    private Controller controller = new Controller();

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Container getContainer(@PathParam("username") String username) {
        return controller.fetchContainer(username);
    }

}

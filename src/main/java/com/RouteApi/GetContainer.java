package com.RouteApi;

import controller.ContainerController;
import model.pojos.Container;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/getcontainer")
public class GetContainer {

    private ContainerController containerController = new ContainerController();

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Container getContainer(@PathParam("username") String username) {
        return containerController.fetchContainer(username);
    }

}

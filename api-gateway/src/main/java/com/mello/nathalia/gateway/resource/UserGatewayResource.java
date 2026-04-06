package com.mello.nathalia.gateway.resource;

import com.mello.nathalia.gateway.client.UserServiceClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserGatewayResource {

    @Inject
    @RestClient
    UserServiceClient userServiceClient;

    @GET
    public Response getAll() {
        return userServiceClient.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return userServiceClient.getById(id);
    }

    @POST
    public Response create(String body) {
        return userServiceClient.create(body);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return userServiceClient.delete(id);
    }
}

package com.mello.nathalia.gateway.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-service")
@RegisterProvider(TokenPropagationFilter.class)
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserServiceClient {

    @GET
    Response getAll();

    @GET
    @Path("/{id}")
    Response getById(@PathParam("id") Long id);

    @POST
    Response create(String body);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Long id);


}

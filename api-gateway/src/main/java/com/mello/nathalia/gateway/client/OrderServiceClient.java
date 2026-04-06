package com.mello.nathalia.gateway.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "order-service")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface OrderServiceClient {

    @GET
    Response getAll();

    @GET
    @Path("/user/{userId}")
    Response getByUserId(@PathParam("userId") Long userId);

    @POST
    Response create(String body, @HeaderParam("Idempotency-Key") String idempotencyKey);
}

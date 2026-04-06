package com.mello.nathalia.gateway.resource;

import com.mello.nathalia.gateway.client.OrderServiceClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderGatewayResource {

    @Inject
    @RestClient
    OrderServiceClient orderServiceClient;

    @GET
    public Response getAll() {
        return orderServiceClient.getAll();
    }

    @GET
    @Path("/user/{userId}")
    public Response getByUserId(@PathParam("userId") Long userId) {
        return orderServiceClient.getByUserId(userId);
    }

    @POST
    public Response create(String body,
                            @HeaderParam("Idempotency-Key") String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return Response.status(400)
                    .entity("{\"code\":\"MISSING_IDEMPOTENCY_KEY\",\"message\":\"Header Idempotency-Key é obrigatório\"}")
                    .build();
        }
        return orderServiceClient.create(body, idempotencyKey);
    }
}

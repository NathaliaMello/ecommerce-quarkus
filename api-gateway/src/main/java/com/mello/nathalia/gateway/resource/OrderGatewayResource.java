package com.mello.nathalia.gateway.resource;

import com.mello.nathalia.gateway.client.OrderServiceClient;
import com.mello.nathalia.gateway.common.response.ErrorResponse;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({"admin", "user"})
    public Response getAll() {
        return orderServiceClient.getAll();
    }

    @GET
    @Path("/user/{userId}")
    @RolesAllowed({"admin", "user"})
    public Response getByUserId(@PathParam("userId") Long userId) {
        return orderServiceClient.getByUserId(userId);
    }

    @POST
    @RolesAllowed("admin")
    public Response create(String body,
                           @HeaderParam("Idempotency-Key") String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return Response.status(400)
                    .entity(new ErrorResponse(
                            "MISSING_IDEMPOTENCY_KEY",
                            "Header Idempotency-Key é obrigatório"))
                    .build();
        }
        return orderServiceClient.create(body, idempotencyKey);
    }
}

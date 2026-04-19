package com.mello.nathalia.notification.infrastructure.client;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "order-service")
@OidcClientFilter
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public interface OrderServiceClient {

    @GET
    @Path("/user/{userId}/summary")
    OrderSummaryResponse getSummaryByUserId(@PathParam("userId") Long userId);
}

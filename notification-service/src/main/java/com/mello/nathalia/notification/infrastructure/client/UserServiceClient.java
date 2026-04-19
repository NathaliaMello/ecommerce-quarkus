package com.mello.nathalia.notification.infrastructure.client;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-service")
@OidcClientFilter
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public interface UserServiceClient {

    @GET
    @Path("/{id}")
    UserResponse getById(@PathParam("id") Long id);
}

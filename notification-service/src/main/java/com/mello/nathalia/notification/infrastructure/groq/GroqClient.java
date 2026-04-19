package com.mello.nathalia.notification.infrastructure.groq;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "groq-api")
@Path("/openai/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface  GroqClient {

    @POST
    @Path("/chat/completions")
    GroqResponse chat(
            @HeaderParam("Authorization") String authorization,
            GroqRequest request);
}

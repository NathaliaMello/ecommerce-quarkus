package com.mello.nathalia.gateway.client;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class TokenPropagationFilter implements ClientRequestFilter {

    @Inject
    jakarta.inject.Provider<ContainerRequestContext> containerRequestContext;

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        try {
            String authHeader = containerRequestContext.get()
                    .getHeaderString(HttpHeaders.AUTHORIZATION);

            if (authHeader != null) {
                requestContext.getHeaders()
                        .putSingle(HttpHeaders.AUTHORIZATION, authHeader);
            }
        } catch (Exception e) {
            // contexto não disponível, segue sem propagar
        }
    }
}

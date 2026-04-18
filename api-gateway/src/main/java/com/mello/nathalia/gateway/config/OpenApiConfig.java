package com.mello.nathalia.gateway.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API Gateway",
                version = "1.0",
                description = "Gateway de entrada do ecommerce"
        )
)
public class OpenApiConfig extends Application {
}

package com.mello.nathalia.user.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "1.0",
                description = "Gerenciamento de usuários do ecommerce"
        )
)
public class OpenApiConfig extends Application { }

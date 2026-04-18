package com.mello.nathalia.order.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Order Service API",
                version = "1.0",
                description = "Gerenciamento de pedidos do ecommerce"
        )
)
public class OpenApiConfig extends Application { }

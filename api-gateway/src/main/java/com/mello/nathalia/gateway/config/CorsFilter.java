package com.mello.nathalia.gateway.config;

import io.quarkus.vertx.http.runtime.filters.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class CorsFilter {
    public void init(@Observes Filters filters) {
        filters.register(rc -> {
            rc.response().headers()
                    .add("Access-Control-Allow-Origin", "http://localhost:4200")
                    .add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS")
                    .add("Access-Control-Allow-Headers", "Content-Type,Authorization,Idempotency-Key")
                    .add("Access-Control-Allow-Credentials", "true");

            if ("OPTIONS".equals(rc.request().method().name())) {
                rc.response().setStatusCode(200).end();
                return;
            }

            rc.next();
        }, 10);
    }

}

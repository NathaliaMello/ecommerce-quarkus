package com.mello.nathalia.notification.domain.service;

import com.mello.nathalia.notification.infrastructure.client.OrderServiceClient;
import com.mello.nathalia.notification.infrastructure.client.OrderSummaryResponse;
import com.mello.nathalia.notification.infrastructure.client.UserResponse;
import com.mello.nathalia.notification.infrastructure.client.UserServiceClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserContextService {

    private static final Logger LOG = Logger.getLogger(UserContextService.class);

    private final OrderServiceClient orderServiceClient;
    private final UserServiceClient userServiceClient;

    @Inject
    public UserContextService(
            @RestClient OrderServiceClient orderServiceClient,
            @RestClient UserServiceClient userServiceClient) {
        this.orderServiceClient = orderServiceClient;
        this.userServiceClient = userServiceClient;
    }

    public String buildContext(Long userId) {
        StringBuilder context = new StringBuilder();

        try {

            UserResponse user = userServiceClient.getById(userId);
            context.append("Nome do cliente: ").append(user.name()).append("\n");
        } catch (Exception e) {
            LOG.warnf("Não foi possível buscar usuário pelo ID %d: %s", userId, e.getMessage());
            context.append("Nome do cliente: não disponível\n");
        }

        try {
            OrderSummaryResponse sumary = orderServiceClient.getSummaryByUserId(userId);
            context.append("Total de pedidos: ").append(sumary.totalOrders()).append("\n");

            if(sumary.totalOrders() == 1) {
                context.append("Observação: este é o primeiro pedido do cliente\n");
            } else if(sumary.totalOrders() > 5) {
                context.append("Observação: cliente frequente com ").append(sumary.totalOrders()).append(" pedidos\n");
            }

        } catch (Exception e) {
            LOG.warnf("Não foi possível buscar o histórico do usuário %d: %s", userId, e.getMessage());
            context.append("Total de pedidos do cliente: não disponível\n");
        }

        return context.toString();
    }

}

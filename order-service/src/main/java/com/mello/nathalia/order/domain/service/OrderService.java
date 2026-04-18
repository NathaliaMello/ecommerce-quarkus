package com.mello.nathalia.order.domain.service;

import com.mello.nathalia.order.client.UserClient;
import com.mello.nathalia.order.client.UserResponse;
import com.mello.nathalia.order.common.response.ErrorResponse;
import com.mello.nathalia.order.domain.model.Order;
import com.mello.nathalia.order.domain.model.OrderCreatedEvent;
import com.mello.nathalia.order.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;

import java.util.Optional;

@ApplicationScoped
public class OrderService {

    private static final org.jboss.logging.Logger Log = org.jboss.logging.Logger.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final UserClient userClient;

    @Channel("order-created")
    MutinyEmitter<OrderCreatedEvent> orderCreatedEmitter;

    @Inject
    public OrderService(OrderRepository orderRepository, @RestClient UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    @CircuitBreaker(
            requestVolumeThreshold = 4,
            failureRatio = 0.5,
            delay = 10000,
            skipOn = {WebApplicationException.class}
    )
    @Timeout(2000)
    @Fallback(
            fallbackMethod = "createFallback",
            skipOn = {WebApplicationException.class}
    )
    @Transactional
    public Order create(Order order, String idempotencyKey) {

        if (idempotencyKey != null) {
            Optional<Order> existing = orderRepository.findByIdempotencyKey(idempotencyKey);
            if (existing.isPresent()) {
                return existing.get();
            }
            order.idempotencyKey = idempotencyKey;
        }

        UserResponse user = userClient.findById(order.userId);

        if (!user.active()) {
            throw new WebApplicationException(
                    jakarta.ws.rs.core.Response.status(422)
                            .entity(new ErrorResponse("USER_INACTIVE", "Usuário inativo"))
                            .build()
            );
        }

        orderRepository.persist(order);

        orderCreatedEmitter.send(new OrderCreatedEvent(
                order.id,
                order.userId,
                order.total.doubleValue(),
                order.status.name()
        )).subscribe().with(
                success -> Log.infof("Evento order.created publicado para pedido #%d", order.id),
                failure -> Log.errorf(failure, "Falha ao publicar evento order.created para pedido #%d", order.id)
        );

        return order;
    }

    public Order createFallback(Order order, String idempotencyKey) {
        throw new WebApplicationException(
                jakarta.ws.rs.core.Response.status(503)
                        .entity(new ErrorResponse(
                                "USER_SERVICE_UNAVAILABLE",
                                "Serviço de usuários temporariamente indisponível. Tente novamente em instantes."
                        ))
                        .build()
        );
    }
}

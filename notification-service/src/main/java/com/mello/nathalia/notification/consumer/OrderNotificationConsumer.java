package com.mello.nathalia.notification.consumer;

import com.mello.nathalia.notification.domain.OrderCreatedEvent;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import org.jboss.logging.Logger;

@ApplicationScoped
public class OrderNotificationConsumer {

    private static final Logger LOG  = Logger.getLogger(OrderNotificationConsumer.class.getName());

    @Incoming("order-created")
    @Blocking
    public void onOrderCreated(OrderCreatedEvent event) {
        LOG.infof("Notificação recebida — Pedido #%d criado para usuário %d. Total: R$ %.2f",
                event.orderId(), event.userId(), event.total());


        LOG.infof("Notificação processada com sucesso para pedido #%d", event.orderId());
    }

}

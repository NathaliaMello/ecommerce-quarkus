package com.mello.nathalia.notification.consumer;

import com.mello.nathalia.notification.domain.OrderCreatedEvent;
import com.mello.nathalia.notification.domain.service.NotificationAiService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import org.jboss.logging.Logger;

@ApplicationScoped
public class OrderNotificationConsumer {

    private static final Logger LOG  = Logger.getLogger(OrderNotificationConsumer.class.getName());

    private final NotificationAiService notificationAiService;

    @Inject
    public OrderNotificationConsumer(NotificationAiService notificationAiService) {
        this.notificationAiService = notificationAiService;
    }

    @Incoming("order-created")
    @Blocking
    public void onOrderCreated(OrderCreatedEvent event) {
        LOG.infof("Evento recebido — Pedido #%d para usuário %d",
                event.orderId(), event.userId(), event.total());

        String notification = notificationAiService.generateNotification(event);

        LOG.infof("Notificação para pedido #%d: %s", event.orderId(), notification);
    }

}

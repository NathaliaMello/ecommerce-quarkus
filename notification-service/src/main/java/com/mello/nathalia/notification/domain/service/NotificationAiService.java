package com.mello.nathalia.notification.domain.service;

import com.mello.nathalia.notification.domain.OrderCreatedEvent;
import com.mello.nathalia.notification.infrastructure.groq.GroqClient;
import com.mello.nathalia.notification.infrastructure.groq.GroqRequest;
import com.mello.nathalia.notification.infrastructure.groq.GroqResponse;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class NotificationAiService {

    private static final Logger LOG = Logger.getLogger(NotificationAiService.class);

    private static final String SYSTEM_PROMPT = """
            Você é um assistente de notificações de um ecommerce brasileiro.
            Gere mensagens curtas, amigáveis e profissionais em português para
            notificar clientes sobre seus pedidos.
            Responda APENAS com o texto da notificação, sem explicações adicionais.
            Máximo de 2 frases.
            """;

    @RestClient
    GroqClient groqClient;

    @ConfigProperty(name = "groq.api.key")
    String apiKey;

    @ConfigProperty(name = "groq.model")
    String model;

    @Inject
    public NotificationAiService(@RestClient GroqClient groqClient) {
        this.groqClient = groqClient;
    }

    public String generateNotification(OrderCreatedEvent event) {
        String userMessage = """
                Pedido #%d criado com sucesso.
                Valor total: R$ %.2f
                Status: %s
                """.formatted(event.orderId(), event.total(), event.status());

        GroqRequest request = new GroqRequest(
                model,
                List.of(
                        new GroqRequest.Message("system", SYSTEM_PROMPT),
                        new GroqRequest.Message("user", userMessage)
                ),
                150,
                0.7
        );

        try {
            GroqResponse response = groqClient.chat("Bearer " + apiKey, request);
            String notification = response.firstContent();

            if (notification == null || notification.isBlank()) {
                LOG.warnf("Resposta vazia da IA para pedido #%d, usando fallback", event.orderId());
                return fallbackNotification(event);
            }

            LOG.infof("Notificação gerada pela IA para pedido #%d: %s",
                    event.orderId(), notification);
            return notification;
        } catch (Exception e) {
            LOG.errorf(e, "Falha ao gerar notificação via IA para pedido #%d", event.orderId());
            return fallbackNotification(event);
        }
    }

    private String fallbackNotification(OrderCreatedEvent event) {
        return "Seu pedido #%d foi recebido com sucesso! Total: R$ %.2f"
                .formatted(event.orderId(), event.total());
    }
}

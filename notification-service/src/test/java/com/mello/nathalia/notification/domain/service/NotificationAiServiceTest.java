package com.mello.nathalia.notification.domain.service;

import com.mello.nathalia.notification.domain.OrderCreatedEvent;
import com.mello.nathalia.notification.infrastructure.groq.GroqClient;
import com.mello.nathalia.notification.infrastructure.groq.GroqResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class NotificationAiServiceTest {

    @InjectMock
    @RestClient
    GroqClient groqClient;

    @Inject
    NotificationAiService notificationAiService;

    private OrderCreatedEvent pedidoExemplo() {
        return new OrderCreatedEvent(1L, 1L, 250.00, "PENDING");
    }

    @Test
    @DisplayName("deve gerar notificação via IA quando Groq responde com sucesso")
    void deveGerarNotificacaoViaIA() {
        // Arrange — prepara o mock para retornar uma resposta válida
        GroqResponse.Message message = new GroqResponse.Message("assistant",
                "Seu pedido #1 foi criado com sucesso!");
        GroqResponse.Choice choice = new GroqResponse.Choice(message);
        GroqResponse groqResponse = new GroqResponse(List.of(choice));

        Mockito.when(groqClient.chat(Mockito.anyString(), Mockito.any()))
                .thenReturn(groqResponse);

        // Act — executa o método que estamos testando
        String resultado = notificationAiService.generateNotification(pedidoExemplo());

        // Assert — verifica o resultado
        assertThat(resultado)
                .isNotNull()
                .isNotBlank()
                .isEqualTo("Seu pedido #1 foi criado com sucesso!");
    }

    @Test
    @DisplayName("deve retornar mensagem de fallback quando Groq lança exceção")
    void deveRetornarFallbackQuandoGroqFalha() {
        // Arrange — mock lança exceção simulando falha da API
        Mockito.when(groqClient.chat(Mockito.anyString(), Mockito.any()))
                .thenThrow(new RuntimeException("Groq indisponível"));

        // Act
        String resultado = notificationAiService.generateNotification(pedidoExemplo());

        // Assert — deve retornar o fallback, nunca lançar exceção
        assertThat(resultado)
                .isNotNull()
                .isNotBlank()
                .contains("#1")
                .contains("250,00");
    }

    @Test
    @DisplayName("deve retornar fallback quando Groq retorna lista de choices vazia")
    void deveRetornarFallbackQuandoRespostaVazia() {
        // Arrange — mock retorna resposta sem choices
        GroqResponse groqResponseVazia = new GroqResponse(List.of());

        Mockito.when(groqClient.chat(Mockito.anyString(), Mockito.any()))
                .thenReturn(groqResponseVazia);

        // Act
        String resultado = notificationAiService.generateNotification(pedidoExemplo());

        // Assert — firstContent() retorna null, deve cair no fallback
        assertThat(resultado)
                .isNotNull()
                .isNotBlank();
    }
}

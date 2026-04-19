package com.mello.nathalia.notification.infrastructure.groq;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GroqRequest(
        String model,
        List<Message> messages,
        @JsonProperty("max_tokens") int maxTokens,
        double temperature) {
    public record Message(
            String role,
            String content) {
    }
}

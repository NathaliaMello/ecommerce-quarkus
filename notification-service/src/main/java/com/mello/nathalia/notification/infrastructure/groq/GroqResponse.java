package com.mello.nathalia.notification.infrastructure.groq;

import java.util.List;

public record GroqResponse(
        List<Choice> choices
) {
    public record Choice(
            Message message) {
    }

    public record Message(
            String role,
            String content) {
    }

    public String firstContent() {
        if (choices == null || choices.isEmpty()) {
            return null;
        }
        return choices.getFirst().message().content();
    }
}

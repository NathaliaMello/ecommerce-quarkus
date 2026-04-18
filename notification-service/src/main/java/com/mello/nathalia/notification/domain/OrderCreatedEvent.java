package com.mello.nathalia.notification.domain;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Double total,
        String status
) { }

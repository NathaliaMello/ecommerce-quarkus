package com.mello.nathalia.notification.consumer;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Double total,
        String status
) { }

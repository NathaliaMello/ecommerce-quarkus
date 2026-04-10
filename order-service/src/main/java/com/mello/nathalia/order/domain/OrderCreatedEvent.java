package com.mello.nathalia.order.domain;

public record OrderCreatedEvent (
        Long orderId,
        Long userId,
        Double total,
        String status
) { }

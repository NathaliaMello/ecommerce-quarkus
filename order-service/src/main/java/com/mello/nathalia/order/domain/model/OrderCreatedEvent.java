package com.mello.nathalia.order.domain.model;

public record OrderCreatedEvent (
        Long orderId,
        Long userId,
        Double total,
        String status
) { }

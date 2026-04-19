package com.mello.nathalia.notification.infrastructure.client;

import java.time.LocalDateTime;

public record OrderSummaryResponse(
        Long userId,
        int totalOrders,
        LocalDateTime firstOrderDate) { }

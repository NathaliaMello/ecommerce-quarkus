package com.mello.nathalia.order.common.response;

import java.time.LocalDateTime;

public record OrderSummaryResponse(
        Long userId,
        int totalOrders,
        LocalDateTime firstOrderDate
) { }

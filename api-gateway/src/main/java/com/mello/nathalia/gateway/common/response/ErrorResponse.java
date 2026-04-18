package com.mello.nathalia.gateway.common.response;

public record ErrorResponse(
        String code,
        String message
) { }

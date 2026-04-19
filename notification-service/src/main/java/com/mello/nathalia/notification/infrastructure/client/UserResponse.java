package com.mello.nathalia.notification.infrastructure.client;

public record UserResponse(
        Long id,
        String name,
        String email,
        boolean active) { }

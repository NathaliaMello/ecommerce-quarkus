package com.mello.nathalia.order.client;

public record UserResponse(
        Long id,
        String email,
        String name,
        boolean active)
{}

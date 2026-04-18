package com.mello.nathalia.order.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "user_id", nullable = false)
    public Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public OrderStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal total;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "idempotency_key", unique = true)
    public String idempotencyKey;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
}

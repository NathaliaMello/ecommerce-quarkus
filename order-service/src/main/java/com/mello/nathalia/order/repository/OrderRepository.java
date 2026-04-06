package com.mello.nathalia.order.repository;

import com.mello.nathalia.order.domain.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findByUserId(Long userId) {
        return list("userId", userId);
    }

    public Optional<Order> findByIdempotencyKey(String key) {
        return find("idempotencyKey", key).firstResultOptional();
    }

}

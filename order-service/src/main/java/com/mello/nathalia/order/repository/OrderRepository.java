package com.mello.nathalia.order.repository;

import com.mello.nathalia.order.domain.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findByUserId(Long userId) {
        return list("userId", userId);
    }

}

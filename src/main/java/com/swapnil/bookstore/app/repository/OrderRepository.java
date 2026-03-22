package com.swapnil.bookstore.app.repository;

import com.swapnil.bookstore.app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByIdempotencyKey(String key);
    Order findByIdempotencyKey(String key);
}


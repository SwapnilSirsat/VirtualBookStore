package com.swapnil.bookstore.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    private Long userId;
    private Double totalAmount;
    private String status;

    @Column(unique = true)
    private String idempotencyKey;
}


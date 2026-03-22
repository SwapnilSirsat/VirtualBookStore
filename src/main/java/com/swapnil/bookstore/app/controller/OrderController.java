package com.swapnil.bookstore.app.controller;

import com.swapnil.bookstore.app.dto.CreateOrderRequest;
import com.swapnil.bookstore.app.entity.Order;
import com.swapnil.bookstore.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public Order place(@RequestBody CreateOrderRequest req) {
        return service.placeOrder(req.getUserId(), req.getIdempotencyKey());
    }
}


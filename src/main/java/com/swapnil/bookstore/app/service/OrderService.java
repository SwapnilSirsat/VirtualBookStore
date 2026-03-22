package com.swapnil.bookstore.app.service;

import com.swapnil.bookstore.app.entity.Book;
import com.swapnil.bookstore.app.entity.Order;
import com.swapnil.bookstore.app.repository.BookRepository;
import com.swapnil.bookstore.app.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {

    private final BookRepository bookRepo;
    private final OrderRepository orderRepo;
    private final CartService cartService;
    private final RedisLockService lockService;

    @Autowired
    public OrderService(BookRepository bookRepo, OrderRepository orderRepo, CartService cartService, RedisLockService lockService) {
        this.bookRepo = bookRepo;
        this.orderRepo = orderRepo;
        this.cartService = cartService;
        this.lockService = lockService;
    }

    @Transactional
    public Order placeOrder(Long userId, String idempotencyKey) {

        if (orderRepo.existsByIdempotencyKey(idempotencyKey))
            return orderRepo.findByIdempotencyKey(idempotencyKey);

        String lockKey = "order:" + userId;
        if (!lockService.lock(lockKey))
            throw new RuntimeException("Concurrent request");

        try {
            Map<Long, Integer> cart = cartService.get(userId);
            double total = 0;

            for (var entry : cart.entrySet()) {
                Book book = bookRepo.lockById(entry.getKey()).orElseThrow();
                if (book.getStock() < entry.getValue())
                    throw new RuntimeException("Out of stock");

                book.setStock(book.getStock() - entry.getValue());
                total += book.getPrice() * entry.getValue();
            }

            Order order = new Order();
            order.setUserId(userId);
            order.setTotalAmount(total);
            order.setStatus("CREATED");
            order.setIdempotencyKey(idempotencyKey);

            cartService.clear(userId);
            return orderRepo.save(order);

        } finally {
            lockService.unlock(lockKey);
        }
    }
}


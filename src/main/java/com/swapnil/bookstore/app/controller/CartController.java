package com.swapnil.bookstore.app.controller;

import com.swapnil.bookstore.app.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public void add(@RequestParam Long userId,
                    @RequestParam Long bookId,
                    @RequestParam int quantity) {
        cartService.add(userId, bookId, quantity);
    }

    @GetMapping("/{userId}")
    public Map<Long, Integer> view(@PathVariable Long userId) {
        return cartService.get(userId);
    }

    @DeleteMapping
    public void clear(@RequestParam Long userId) {
        cartService.clear(userId);
    }
}


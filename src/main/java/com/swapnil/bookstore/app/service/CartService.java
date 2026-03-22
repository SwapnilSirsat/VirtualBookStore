package com.swapnil.bookstore.app.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {
    
    // cart service is working fine

    private final RedisTemplate<String, Map<Long, Integer>> redis;


    public CartService(@Qualifier("redisTemplateMap") RedisTemplate<String, Map<Long, Integer>> redis) {
        this.redis = redis;
    }

    public void add(Long userId, Long bookId, int qty) {
        String key = "cart:" + userId;
        redis.opsForHash().increment(key, String.valueOf(bookId), qty);
        // later add this cart to db too;
    }

    public Map<Long, Integer> get(Long userId) {
        try {
            return redis.opsForHash().entries("cart:" + userId).entrySet().stream().collect(Collectors.toMap(
                    entry -> Long.parseLong((String) entry.getKey()),     // Key Mapper: Parse String key to Long
                    entry -> (Integer) entry.getValue(),// Value Mapper: Cast Object value to Integer
                    (oldValue, newValue) -> oldValue,   // Merge function for duplicate keys (optional, but good practice)
                    HashMap::new                       // Supplier to ensure a HashMap is created
            ));
        } catch (Exception e) {
            return Map.of();
        }
    }

    public void clear(Long userId) {
        redis.delete("cart:" + userId);
    }
}


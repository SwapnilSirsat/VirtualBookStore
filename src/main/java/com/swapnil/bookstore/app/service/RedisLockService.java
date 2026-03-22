package com.swapnil.bookstore.app.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class RedisLockService {

    private final RedisTemplate<String, String> redis;

    public RedisLockService(@Qualifier("redisTemplateString") RedisTemplate<String, String> redis) {
        this.redis = redis;
    }

    public boolean lock(String key) {
        return Boolean.TRUE.equals(redis.opsForValue().setIfAbsent(key, "LOCK", Duration.ofSeconds(10)));
    }

    public void unlock(String key) {
        redis.delete(key);
    }
}


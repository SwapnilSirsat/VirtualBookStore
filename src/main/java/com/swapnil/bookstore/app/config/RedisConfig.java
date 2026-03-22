package com.swapnil.bookstore.app.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swapnil.bookstore.app.dto.BookResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, List<BookResponse>> redisTemplate(
            RedisConnectionFactory factory) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        JavaType javaType = mapper.getTypeFactory()
                .constructCollectionType(List.class, BookResponse.class);

        Jackson2JsonRedisSerializer<List<BookResponse>> serializer =
                new Jackson2JsonRedisSerializer<>(javaType);

        RedisTemplate<String, List<BookResponse>> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, Map<Long, Integer>> redisTemplateMap(
            RedisConnectionFactory factory) {
        RedisTemplate<String, Map<Long, Integer>> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateString(
            RedisConnectionFactory factory) {
        RedisTemplate<String,String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }
}

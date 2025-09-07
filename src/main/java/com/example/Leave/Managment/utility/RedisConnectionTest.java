package com.example.Leave.Managment.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTest implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) {
        try {
            // Save test value
            redisTemplate.opsForValue().set("testKey", "Hello Redis");

            // Read test value
            String value = (String) redisTemplate.opsForValue().get("testKey");
            System.out.println("✅ Redis connection OK, value = " + value);
        } catch (Exception e) {
            System.out.println("❌ Redis connection FAILED: " + e.getMessage());
        }
    }
}


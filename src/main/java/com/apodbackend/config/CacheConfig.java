package com.apodbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;


@Configuration
public class CacheConfig {

    @Value("${apod.cache.max-size:500}")
    private int maxSize;

    @Value("${apod.cache.expire-seconds:86400}")
    private int expireSeconds;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager("apod");
        manager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .maximumSize(maxSize));
        return manager;
    }
}

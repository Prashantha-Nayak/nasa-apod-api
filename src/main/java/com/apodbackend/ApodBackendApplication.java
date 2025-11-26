package com.apodbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching 
public class ApodBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApodBackendApplication.class, args);
    }
}

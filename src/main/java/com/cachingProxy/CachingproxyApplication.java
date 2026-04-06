package com.cachingProxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CachingproxyApplication implements CommandLineRunner {

    @Value("${origin.url}")
    private String originUrl;

    public static void main(String[] args) {
        SpringApplication.run(CachingproxyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("App started....");
    }

    @Bean
    public Map<String, String> cache() {
        return new HashMap<>();
    }
}

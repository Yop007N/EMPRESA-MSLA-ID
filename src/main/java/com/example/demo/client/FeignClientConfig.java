package com.example.demo.client;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {

    @Value("${apilayer.api-key}")
    private String apiKey;

    @Bean
    RequestInterceptor apiKeyInterceptor() {
        return template -> template.header("apikey", apiKey);
    }
}

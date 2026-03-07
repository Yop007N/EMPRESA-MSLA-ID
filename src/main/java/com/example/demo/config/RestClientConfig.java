package com.example.demo.config;

import com.example.demo.client.ExchangeRateApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    ExchangeRateApiClient exchangeRateApiClient(
            @Value("${apilayer.api-key}") String apiKey,
            @Value("${apilayer.base-url}") String baseUrl) {

        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("apikey", apiKey)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ExchangeRateApiClient.class);
    }
}

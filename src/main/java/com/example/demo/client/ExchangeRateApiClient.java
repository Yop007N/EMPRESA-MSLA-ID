package com.example.demo.client;

import com.example.demo.client.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "exchangeRateApi",
        url = "${apilayer.base-url}",
        configuration = FeignClientConfig.class
)
public interface ExchangeRateApiClient {

    @GetMapping("/convert")
    ExchangeRateResponse convert(
            @RequestParam("to") String to,
            @RequestParam("from") String from,
            @RequestParam("amount") Double amount
    );
}

package com.example.demo.client;

import com.example.demo.client.dto.ExchangeRateResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ExchangeRateApiClient {

    @GetExchange("/convert")
    ExchangeRateResponse convert(
            @RequestParam("to") String to,
            @RequestParam("from") String from,
            @RequestParam("amount") Double amount
    );
}

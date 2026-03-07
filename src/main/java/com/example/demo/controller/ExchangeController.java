package com.example.demo.controller;

import com.example.demo.generated.api.ExchangeApi;
import com.example.demo.generated.model.ConvertRequest;
import com.example.demo.generated.model.ConvertResponse;
import com.example.demo.generated.model.CurrencySummaryDto;
import com.example.demo.generated.model.ExchangeHistoryDto;
import com.example.demo.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExchangeController implements ExchangeApi {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @Override
    public ResponseEntity<ConvertResponse> convertCurrency(ConvertRequest request) {
        return ResponseEntity.ok(exchangeService.convert(request));
    }

    @Override
    public ResponseEntity<List<ExchangeHistoryDto>> getHistory(LocalDate from, LocalDate to, String currency) {
        return ResponseEntity.ok(exchangeService.getHistory(from, to, currency));
    }

    @Override
    public ResponseEntity<List<CurrencySummaryDto>> getSummary() {
        return ResponseEntity.ok(exchangeService.getSummary());
    }
}

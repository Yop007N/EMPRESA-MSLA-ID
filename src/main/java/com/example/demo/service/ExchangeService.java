package com.example.demo.service;

import com.example.demo.client.ExchangeRateApiClient;
import com.example.demo.client.dto.ExchangeRateResponse;
import com.example.demo.entity.ExchangeHistory;
import com.example.demo.generated.model.ConvertRequest;
import com.example.demo.generated.model.ConvertResponse;
import com.example.demo.generated.model.CurrencySummaryDto;
import com.example.demo.generated.model.ExchangeHistoryDto;
import com.example.demo.repository.CurrencySummary;
import com.example.demo.repository.ExchangeHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeService {

    private final ExchangeRateApiClient apiClient;
    private final ExchangeHistoryRepository repository;

    public ExchangeService(ExchangeRateApiClient apiClient, ExchangeHistoryRepository repository) {
        this.apiClient = apiClient;
        this.repository = repository;
    }

    public ConvertResponse convert(ConvertRequest request) {
        ExchangeRateResponse apiResponse = apiClient.convert(
                request.getTo(),
                request.getFrom(),
                request.getAmount()
        );

        ExchangeHistory history = new ExchangeHistory();
        history.setFromCurrency(request.getFrom());
        history.setToCurrency(request.getTo());
        history.setAmount(request.getAmount());
        history.setConvertedAmount(apiResponse.getResult());
        history.setRate(apiResponse.getInfo().getRate());
        history.setDate(LocalDate.parse(apiResponse.getDate()));
        history.setCreatedAt(LocalDateTime.now());
        repository.save(history);

        ConvertResponse response = new ConvertResponse();
        response.setFrom(request.getFrom());
        response.setTo(request.getTo());
        response.setAmount(request.getAmount());
        response.setConvertedAmount(apiResponse.getResult());
        response.setRate(apiResponse.getInfo().getRate());
        response.setDate(LocalDate.parse(apiResponse.getDate()));
        return response;
    }

    public List<ExchangeHistoryDto> getHistory(LocalDate from, LocalDate to, String currency) {
        List<ExchangeHistory> records = repository.findByDateBetween(from, to);

        if (currency != null && !currency.isBlank()) {
            records = records.stream()
                    .filter(h -> h.getFromCurrency().equalsIgnoreCase(currency)
                              || h.getToCurrency().equalsIgnoreCase(currency))
                    .toList();
        }

        return records.stream().map(this::toDto).toList();
    }

    public List<CurrencySummaryDto> getSummary() {
        return repository.getSummaryByTargetCurrency().stream()
                .map(this::toSummaryDto)
                .toList();
    }

    private ExchangeHistoryDto toDto(ExchangeHistory h) {
        ExchangeHistoryDto dto = new ExchangeHistoryDto();
        dto.setId(h.getId());
        dto.setFrom(h.getFromCurrency());
        dto.setTo(h.getToCurrency());
        dto.setAmount(h.getAmount());
        dto.setConvertedAmount(h.getConvertedAmount());
        dto.setRate(h.getRate());
        dto.setDate(h.getDate());
        return dto;
    }

    private CurrencySummaryDto toSummaryDto(CurrencySummary s) {
        CurrencySummaryDto dto = new CurrencySummaryDto();
        dto.setCurrency(s.getCurrency());
        dto.setTotalConverted(s.getTotalConverted());
        return dto;
    }
}

package com.example.demo.service;

import com.example.demo.client.ExchangeRateApiClient;
import com.example.demo.client.dto.ExchangeRateInfo;
import com.example.demo.client.dto.ExchangeRateResponse;
import com.example.demo.entity.ExchangeHistory;
import com.example.demo.generated.model.ConvertRequest;
import com.example.demo.generated.model.ConvertResponse;
import com.example.demo.generated.model.CurrencySummaryDto;
import com.example.demo.generated.model.ExchangeHistoryDto;
import com.example.demo.repository.CurrencySummary;
import com.example.demo.repository.ExchangeHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {

    @Mock
    ExchangeRateApiClient apiClient;

    @Mock
    ExchangeHistoryRepository repository;

    @InjectMocks
    ExchangeService exchangeService;

    @Test
    void convert_shouldReturnConvertedAmountAndSaveHistory() {
        ConvertRequest request = new ConvertRequest();
        request.setFrom("USD");
        request.setTo("PEN");
        request.setAmount(100.0);

        ExchangeRateInfo info = new ExchangeRateInfo();
        info.setRate(3.74);

        ExchangeRateResponse apiResponse = new ExchangeRateResponse();
        apiResponse.setSuccess(true);
        apiResponse.setResult(374.0);
        apiResponse.setDate("2025-04-15");
        apiResponse.setInfo(info);

        when(apiClient.convert("PEN", "USD", 100.0)).thenReturn(apiResponse);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ConvertResponse result = exchangeService.convert(request);

        assertThat(result.getFrom()).isEqualTo("USD");
        assertThat(result.getTo()).isEqualTo("PEN");
        assertThat(result.getAmount()).isEqualTo(100.0);
        assertThat(result.getConvertedAmount()).isEqualTo(374.0);
        assertThat(result.getRate()).isEqualTo(3.74);
        verify(repository).save(any(ExchangeHistory.class));
    }

    @Test
    void getHistory_shouldReturnAllRecordsWhenNoCurrencyFilter() {
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 12, 31);

        ExchangeHistory h1 = buildHistory(1L, "USD", "PEN", 100.0, 374.0, 3.74, LocalDate.of(2025, 4, 15));
        ExchangeHistory h2 = buildHistory(2L, "PEN", "USD", 500.0, 133.7, 0.2674, LocalDate.of(2025, 4, 16));

        when(repository.findByDateBetween(from, to)).thenReturn(List.of(h1, h2));

        List<ExchangeHistoryDto> result = exchangeService.getHistory(from, to, null);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFrom()).isEqualTo("USD");
        assertThat(result.get(1).getFrom()).isEqualTo("PEN");
    }

    @Test
    void getHistory_shouldFilterByCurrencyWhenProvided() {
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 12, 31);

        ExchangeHistory h1 = buildHistory(1L, "USD", "PEN", 100.0, 374.0, 3.74, LocalDate.of(2025, 4, 15));
        ExchangeHistory h2 = buildHistory(2L, "EUR", "PEN", 200.0, 800.0, 4.0, LocalDate.of(2025, 4, 16));

        when(repository.findByDateBetween(from, to)).thenReturn(List.of(h1, h2));

        List<ExchangeHistoryDto> result = exchangeService.getHistory(from, to, "USD");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFrom()).isEqualTo("USD");
    }

    @Test
    void getSummary_shouldGroupByCurrencyAndSumTotals() {
        CurrencySummary penSummary = mock(CurrencySummary.class);
        when(penSummary.getCurrency()).thenReturn("PEN");
        when(penSummary.getTotalConverted()).thenReturn(1500.0);

        CurrencySummary usdSummary = mock(CurrencySummary.class);
        when(usdSummary.getCurrency()).thenReturn("USD");
        when(usdSummary.getTotalConverted()).thenReturn(200.0);

        when(repository.getSummaryByTargetCurrency()).thenReturn(List.of(penSummary, usdSummary));

        List<CurrencySummaryDto> result = exchangeService.getSummary();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCurrency()).isEqualTo("PEN");
        assertThat(result.get(0).getTotalConverted()).isEqualTo(1500.0);
        assertThat(result.get(1).getCurrency()).isEqualTo("USD");
        assertThat(result.get(1).getTotalConverted()).isEqualTo(200.0);
    }

    private ExchangeHistory buildHistory(Long id, String from, String to,
                                         Double amount, Double converted, Double rate, LocalDate date) {
        ExchangeHistory h = new ExchangeHistory();
        h.setId(id);
        h.setFromCurrency(from);
        h.setToCurrency(to);
        h.setAmount(amount);
        h.setConvertedAmount(converted);
        h.setRate(rate);
        h.setDate(date);
        h.setCreatedAt(LocalDateTime.now());
        return h;
    }
}

package com.example.demo.repository;

import com.example.demo.entity.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Long> {

    List<ExchangeHistory> findByDateBetween(LocalDate from, LocalDate to);

    @Query("SELECT e.toCurrency AS currency, SUM(e.convertedAmount) AS totalConverted " +
           "FROM ExchangeHistory e GROUP BY e.toCurrency")
    List<CurrencySummary> getSummaryByTargetCurrency();
}

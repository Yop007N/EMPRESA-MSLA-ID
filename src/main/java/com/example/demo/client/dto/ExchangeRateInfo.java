package com.example.demo.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateInfo {

    private Double rate;
    private Long timestamp;

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}

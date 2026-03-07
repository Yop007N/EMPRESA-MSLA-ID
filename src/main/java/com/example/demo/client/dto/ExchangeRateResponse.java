package com.example.demo.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {

    private boolean success;
    private String date;
    private ExchangeRateInfo info;
    private Double result;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public ExchangeRateInfo getInfo() { return info; }
    public void setInfo(ExchangeRateInfo info) { this.info = info; }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }
}

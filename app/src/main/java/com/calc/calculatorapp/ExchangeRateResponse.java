package com.calc.calculatorapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExchangeRateResponse {

    @SerializedName("currency")
    private String currency;

    @SerializedName("code")
    private String code;

    @SerializedName("rates")
    private List<ExchangeRate> rates;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<ExchangeRate> rates) {
        this.rates = rates;
    }
}
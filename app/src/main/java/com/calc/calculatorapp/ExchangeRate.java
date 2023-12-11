package com.calc.calculatorapp;

import com.google.gson.annotations.SerializedName;

public class ExchangeRate {

    @SerializedName("no")
    private String no;

    @SerializedName("effectiveDate")
    private String effectiveDate;

    @SerializedName("mid")
    private double mid;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }
}
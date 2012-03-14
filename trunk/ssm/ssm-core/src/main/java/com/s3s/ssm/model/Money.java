package com.s3s.ssm.model;

public class Money {
    private String currencyCode;
    private Long value;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public static Money zero(String currencyCode) {
        return Money.create(currencyCode, 0L);
    }

    public static Money create(String currencyCode, Long value) {
        Money money = new Money();
        money.setCurrencyCode(currencyCode);
        money.setValue(value);
        return money;
    }
}

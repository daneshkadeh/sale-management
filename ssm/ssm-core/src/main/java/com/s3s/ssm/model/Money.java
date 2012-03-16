package com.s3s.ssm.model;

import org.apache.commons.lang.ObjectUtils;

/**
 * @author Phan Hong Phuc
 * @since Mar 16, 2012
 */
public class Money {
    // TODO Bang: should we change currencyCode to enum type.
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

    @Override
    public String toString() {
        String v = (value == null) ? "" : value.toString();
        String c = currencyCode == null ? "" : currencyCode;
        return v + " " + c;
    }

    @Override
    public int hashCode() {
        int vh = value == null ? 0 : value.hashCode();
        int ch = currencyCode == null ? 0 : currencyCode.hashCode();
        return vh * 3 + ch * 12;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Money) {
            Money m = (Money) obj;
            return ObjectUtils.equals(value, m.value) && ObjectUtils.equals(currencyCode, m.getCurrencyCode());
        }
        return super.equals(obj);
    }

}

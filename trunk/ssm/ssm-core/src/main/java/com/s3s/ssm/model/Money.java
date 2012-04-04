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

    // private int digit;

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

    /**
     * Operator +
     */
    public Money plus(Money money) {
        if (!currencyCode.equalsIgnoreCase(money.getCurrencyCode())) {
            throw new IllegalArgumentException("To plus, the money codes must be the same");
        }
        return create(currencyCode, value + money.value);
    }

    /**
     * Operator -
     */
    public Money minus(Money money) {
        if (!currencyCode.equalsIgnoreCase(money.getCurrencyCode())) {
            throw new IllegalArgumentException("To plus, the money codes must be the same");
        }
        return create(currencyCode, value - money.getValue());
    }

    /**
     * Operator *
     */
    public Money multiply(double num) {
        return create(currencyCode, Math.round(value * num));
    }

    public Money multiply(int num) {
        return create(currencyCode, value * num);
    }

    /**
     * Operator /
     */
    public Money divide(double num) {
        return create(currencyCode, Math.round(value / num));
    }

    /**
     * Operator /
     */
    public Money divide(int num) {
        return create(currencyCode, value / num);
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

    // public int getDigit() {
    // return digit;
    // }
    //
    // public void setDigit(int digit) {
    // this.digit = digit;
    // }

}

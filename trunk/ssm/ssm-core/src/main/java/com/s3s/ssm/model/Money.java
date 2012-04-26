package com.s3s.ssm.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.util.Assert;

/**
 * @author Phan Hong Phuc
 * @since Mar 16, 2012
 */
@Embeddable
public class Money implements Comparable<Money>, Serializable {
    private static final long serialVersionUID = -8273560720653337969L;
    private CurrencyEnum currencyCode;
    private Long value;

    @Enumerated(EnumType.STRING)
    public CurrencyEnum getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyEnum currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * This method currently use Math.round to round the value. TODO: define how to round later in configuration of
     * sofware.
     * 
     * @param amount
     * @param money
     * @return
     */
    public static Money multiply(Double amount, Money money) {
        Money result = new Money();
        result.setCurrencyCode(money.getCurrencyCode());
        result.setValue(Math.round(amount * money.getValue()));
        return result;
    }

    public static Money multiply(Integer amount, Money money) {
        Money result = new Money();
        result.setCurrencyCode(money.getCurrencyCode());
        result.setValue(amount * money.getValue());
        return result;
    }

    public static Money multiply(Long amount, Money money) {
        Money result = new Money();
        result.setCurrencyCode(money.getCurrencyCode());
        result.setValue(amount * money.getValue());
        return result;
    }

    public static Money zero(CurrencyEnum currencyCode) {
        return Money.create(currencyCode, 0L);
    }

    public static Money create(CurrencyEnum currencyCode, Long value) {
        Money money = new Money();
        money.setCurrencyCode(currencyCode);
        money.setValue(value);
        return money;
    }

    /**
     * Operator +
     */
    public Money plus(Money money) {
        if (currencyCode != money.getCurrencyCode()) {
            throw new IllegalArgumentException("To plus, the money codes must be the same");
        }
        return create(currencyCode, value + money.value);
    }

    /**
     * Operator -
     */
    public Money minus(Money money) {
        if (currencyCode != money.getCurrencyCode()) {
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
        return v + " " + currencyCode.toString();
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

    @Override
    public int compareTo(Money m) {
        Assert.isTrue(currencyCode.equals(m.getCurrencyCode()));
        return value.compareTo(m.getValue());
    }

    // public int getDigit() {
    // return digit;
    // }
    //
    // public void setDigit(int digit) {
    // this.digit = digit;
    // }

}

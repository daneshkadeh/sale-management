/*
 * Payment
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.entity.finance;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "finance_payment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment extends AbstractCodeOLObject {
    private static final long serialVersionUID = 4824284933146267482L;
    private PaymentContent paymentContent;
    private Date paymentDate = new Date();
    private Partner partner;
    private Operator operator;
    private PaymentMode paymentMode;
    private Money amount;
    private Double exchgValue; // compared to default currency
    private Integer rate;
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_content_id")
    @NotNull
    public PaymentContent getPaymentContent() {
        return paymentContent;
    }

    public void setPaymentContent(PaymentContent paymentContent) {
        this.paymentContent = paymentContent;
    }

    @Column(name = "payment_date")
    @NotNull
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    @Column(name = "exchange_value")
    public Double getExchgValue() {
        return exchgValue;
    }

    public void setExchgValue(Double exchgValue) {
        this.exchgValue = exchgValue;
    }

    @Column(name = "payment_mode")
    @Enumerated(EnumType.STRING)
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    @ManyToOne
    @JoinColumn(name = "partner_id")
    @NotNull
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @ManyToOne
    @JoinColumn(name = "operator_id")
    @NotNull
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Column(name = "rate")
    @Digits(fraction = 0, integer = 10)
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "s_payment")
public class Payment extends AbstractIdOLObject {
    // private Invoice invoice;
    // private Contact contact;
    private PaymentType paymentType;
    private Double money;
    private String currency = "VND";
    private PaymentMeanEnum paymentMean;
    private PaymentStatus status = PaymentStatus.OPEN;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "invoice_id")
    // public Invoice getInvoice() {
    // return invoice;
    // }
    //
    // public void setInvoice(Invoice invoice) {
    // this.invoice = invoice;
    // }
    //
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "contact_id")
    // public Contact getContact() {
    // return contact;
    // }
    //
    // public void setContact(Contact contact) {
    // this.contact = contact;
    // }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_type_id")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "money", nullable = false)
    @NotNull
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Column(name = "currency", nullable = false)
    @NotNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "payment_mean", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public PaymentMeanEnum getPaymentMean() {
        return paymentMean;
    }

    public void setPaymentMean(PaymentMeanEnum paymentMean) {
        this.paymentMean = paymentMean;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}

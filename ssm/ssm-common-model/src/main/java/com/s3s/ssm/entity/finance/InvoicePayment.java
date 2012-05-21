/*
 * ContractPayment
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.entity.finance;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.model.Money;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "finance_invoice_payment")
@PrimaryKeyJoinColumn(name = "invoice_payment_id")
public class InvoicePayment extends Payment {
    private static final long serialVersionUID = -1695893616913760376L;
    private Money prePaidAmt;
    private Money remainingAmt;
    private Money custDebt;
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "invoice_id")
    @NotNull
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "pre_paid_amt")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "pre_currency_code")) })
    public Money getPrePaidAmt() {
        return prePaidAmt;
    }

    public void setPrePaidAmt(Money prePaidAmt) {
        this.prePaidAmt = prePaidAmt;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "cust_debt_amt")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "cust_debt_currency_code")) })
    public Money getCustDebt() {
        return custDebt;
    }

    public void setCustDebt(Money custDebt) {
        this.custDebt = custDebt;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "remaining_amt")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "remaining_currency_code")) })
    public Money getRemainingAmt() {
        return remainingAmt;
    }

    public void setRemainingAmt(Money remainingAmt) {
        this.remainingAmt = remainingAmt;
    }

}

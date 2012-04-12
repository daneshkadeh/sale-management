/*
 * Invoice
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
package com.s3s.ssm.entity.sales;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.contact.Partner;

@Entity
@Table(name = "s_invoice")
public class Invoice extends AbstractIdOLObject {
    private static final long serialVersionUID = 5993442648457138659L;
    private String invoiceNumber;
    private InvoiceType type;
    private Partner contact;
    private Date createdDate;
    private Double moneyBeforeTax = 0.0;
    private Double moneyOfTax = 0.0;
    private Double moneyAfterTax = 0.0;
    private String currency = "VND";
    private InvoiceStatus status = InvoiceStatus.OPEN;
    private InvoicePaymentStatus paymentStatus = InvoicePaymentStatus.NO_PAYMENT;
    private Set<DetailInvoice> detailInvoices = new HashSet<>();

    @Column(name = "invoice_number", nullable = false, length = 32)
    @NotNull
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * InvoiceNumber is generated from a sequence table in database. (or built from invoiceId)
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column(name = "invoice_type", nullable = false, length = 32)
    @NotNull
    @Enumerated(EnumType.STRING)
    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    public Partner getContact() {
        return contact;
    }

    public void setContact(Partner contact) {
        this.contact = contact;
    }

    @Column(name = "created_date", nullable = false)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "money_before_tax", nullable = false)
    @NotNull
    public Double getMoneyBeforeTax() {
        return moneyBeforeTax;
    }

    public void setMoneyBeforeTax(Double moneyBeforeTax) {
        this.moneyBeforeTax = moneyBeforeTax;
    }

    @Column(name = "money_of_tax", nullable = false)
    @NotNull
    public Double getMoneyOfTax() {
        return moneyOfTax;
    }

    public void setMoneyOfTax(Double moneyOfTax) {
        this.moneyOfTax = moneyOfTax;
    }

    @Column(name = "money_after_tax", nullable = false)
    @NotNull
    public Double getMoneyAfterTax() {
        return moneyAfterTax;
    }

    public void setMoneyAfterTax(Double moneyAfterTax) {
        this.moneyAfterTax = moneyAfterTax;
    }

    @Column(name = "currency", nullable = false)
    @NotNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    @Column(name = "payment_status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public InvoicePaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(InvoicePaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "invoice")
    public Set<DetailInvoice> getDetailInvoices() {
        return detailInvoices;
    }

    public void setDetailInvoices(Set<DetailInvoice> detailInvoices) {
        this.detailInvoices = detailInvoices;
    }
}

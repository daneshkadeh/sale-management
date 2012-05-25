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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.contact.Individual;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

@Entity
@Table(name = "s_invoice")
public class Invoice extends AbstractIdOLObject {
    private static final long serialVersionUID = 5993442648457138659L;
    private String invoiceNumber;
    private InvoiceType type;
    private Invoice originInvoice;
    private Partner contact; // nguoi, to chuc thanh toan tien hang
    private Individual individual; // ca nhan nhan hang hoa
    private Operator staff;
    private Date createdDate;
    private Money moneyBeforeTax = Money.zero(CurrencyEnum.VND);
    private Money moneyOfTax = Money.zero(CurrencyEnum.VND);
    private Money moneyAfterTax = Money.zero(CurrencyEnum.VND);
    private InvoiceStatus status = InvoiceStatus.OPEN;
    private InvoicePaymentStatus paymentStatus = InvoicePaymentStatus.NO_PAYMENT;
    private InvoiceStoreStatus storeStatus = InvoiceStoreStatus.NO_ACTION;

    private Set<DetailInvoice> detailInvoices = new HashSet<>();

    // list commissions which are apply to this invoice
    private Set<Commission> commissions = new HashSet<>();

    private String remark;

    // Invoice type SUPPORTEE, TRIAL, RENT, customers must return the goods at usedEndDate.
    private Long usedTimeSpan = 0L;
    private Date usedStartDate = new Date();
    private Date usedEndDate;

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
    @JoinColumn(name = "origin_invoice_id")
    public Invoice getOriginInvoice() {
        return originInvoice;
    }

    public void setOriginInvoice(Invoice originInvoice) {
        this.originInvoice = originInvoice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    public Partner getContact() {
        return contact;
    }

    public void setContact(Partner contact) {
        this.contact = contact;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "individual_id")
    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    @NotNull
    public Operator getStaff() {
        return staff;
    }

    public void setStaff(Operator staff) {
        this.staff = staff;
    }

    @Column(name = "created_date")
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_before_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_money_before_tax")) })
    @NotNull
    public Money getMoneyBeforeTax() {
        return moneyBeforeTax;
    }

    public void setMoneyBeforeTax(Money moneyBeforeTax) {
        this.moneyBeforeTax = moneyBeforeTax;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_of_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_money_of_tax")) })
    @NotNull
    public Money getMoneyOfTax() {
        return moneyOfTax;
    }

    public void setMoneyOfTax(Money moneyOfTax) {
        this.moneyOfTax = moneyOfTax;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_after_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_money_after_tax")) })
    @NotNull
    public Money getMoneyAfterTax() {
        return moneyAfterTax;
    }

    public void setMoneyAfterTax(Money moneyAfterTax) {
        this.moneyAfterTax = moneyAfterTax;
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

    @Column(name = "store_status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public InvoiceStoreStatus getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(InvoiceStoreStatus storeStatus) {
        this.storeStatus = storeStatus;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "invoice")
    @Cascade(value = { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.DELETE })
    public Set<DetailInvoice> getDetailInvoices() {
        return detailInvoices;
    }

    public void setDetailInvoices(Set<DetailInvoice> detailInvoices) {
        this.detailInvoices = detailInvoices;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "invoice")
    @Cascade(value = { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.DELETE })
    public Set<Commission> getCommissions() {
        return commissions;
    }

    public void setCommissions(Set<Commission> commissions) {
        this.commissions = commissions;
    }

    @Column(name = "remark", length = 1024)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "used_timespan")
    public Long getUsedTimeSpan() {
        return usedTimeSpan;
    }

    public void setUsedTimeSpan(Long usedTimeSpan) {
        this.usedTimeSpan = usedTimeSpan;
    }

    @Column(name = "used_start_date")
    public Date getUsedStartDate() {
        return usedStartDate;
    }

    public void setUsedStartDate(Date usedStartDate) {
        this.usedStartDate = usedStartDate;
    }

    @Column(name = "used_end_date")
    public Date getUsedEndDate() {
        return usedEndDate;
    }

    public void setUsedEndDate(Date usedEndDate) {
        this.usedEndDate = usedEndDate;
    }

    /**
     * To know the status goods of the invoice.
     * 
     */
    public enum InvoiceStoreStatus {
        NO_ACTION, EXPORTING, EXPORTED, IMPORTING, IMPORTED;

        @Override
        public String toString() {
            return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
        }
    }
}

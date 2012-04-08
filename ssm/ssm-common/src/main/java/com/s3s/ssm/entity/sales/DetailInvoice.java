/*
 * DetailInvoice
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;

@Entity
@Table(name = "s_detail_invoice")
public class DetailInvoice extends AbstractIdOLObject {
    private Invoice invoice;
    private Item item;
    private PackageLine packageLine;
    private Integer amount;
    private Double priceBeforeTax = 0.0;
    private Double priceOfTax = 0.0;
    private Double priceAfterTax = 0.0;
    private Double moneyBeforeTax = 0.0;
    private Double moneyOfTax = 0.0;
    private Double moneyAfterTax = 0.0;
    private String currency = "VND";
    private DetailInvoiceType type = DetailInvoiceType.SALES;
    private DetailInvoiceStatus status = DetailInvoiceStatus.OPEN;

    // Transient field
    private Double totalAmount;

    @Transient
    public Double getTotalAmount() {
        return totalAmount;
    }

    // Please remove this set method because it has no meaning and can be raise potential bugs (its value is calculated
    // from the other). Remember to set editable is false if using this field in List view
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void updateTotalAmount() {
        totalAmount = amount * priceAfterTax;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "packageline_id")
    public PackageLine getPackageLine() {
        return packageLine;
    }

    public void setPackageLine(PackageLine packageLine) {
        this.packageLine = packageLine;
    }

    @Column(name = "amount", nullable = false)
    @NotNull
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
        updateTotalAmount();
    }

    @Column(name = "price_after_tax", nullable = false)
    @NotNull
    public Double getPriceAfterTax() {
        return priceAfterTax;
    }

    public void setPriceAfterTax(Double priceAfterTax) {
        this.priceAfterTax = priceAfterTax;
        updateTotalAmount();
    }

    @Column(name = "price_before_tax", nullable = false)
    @NotNull
    public Double getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setPriceBeforeTax(Double priceBeforeTax) {
        this.priceBeforeTax = priceBeforeTax;
    }

    @Column(name = "price_of_tax", nullable = false)
    @NotNull
    public Double getPriceOfTax() {
        return priceOfTax;
    }

    public void setPriceOfTax(Double priceOfTax) {
        this.priceOfTax = priceOfTax;
        updateTotalAmount();
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

    @Column(name = "detail_invoice_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public DetailInvoiceType getType() {
        return type;
    }

    public void setType(DetailInvoiceType type) {
        this.type = type;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public DetailInvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(DetailInvoiceStatus status) {
        this.status = status;
    }

}

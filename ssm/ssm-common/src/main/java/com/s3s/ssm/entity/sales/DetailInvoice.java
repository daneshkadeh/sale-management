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

import javax.persistence.CascadeType;
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
    private int amount;
    private double priceBeforeTax = 0.0;
    private double priceOfTax = 0.0;
    private double priceAfterTax = 0.0;
    private double moneyBeforeTax = 0.0;
    private double moneyOfTax = 0.0;
    private double moneyAfterTax = 0.0;
    private String currency = "VND";
    private DetailInvoiceType type = DetailInvoiceType.SALES;
    private DetailInvoiceStatus status = DetailInvoiceStatus.OPEN;

    // Transient field
    private double totalAmount;

    @Transient
    public double getTotalAmount() {
        return totalAmount;
    }

    // Please remove this set method because it has no meaning and can be raise potential bugs (its value is calculated
    // from the other). Remember to set editable is false if using this field in List view
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void updateTotalAmount() {
        totalAmount = amount * priceAfterTax;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        updateTotalAmount();
    }

    @Column(name = "price_after_tax")
    public double getPriceAfterTax() {
        return priceAfterTax;
    }

    public void setPriceAfterTax(double priceAfterTax) {
        this.priceAfterTax = priceAfterTax;
        updateTotalAmount();
    }

    @Column(name = "price_before_tax", nullable = false)
    public double getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setPriceBeforeTax(double priceBeforeTax) {
        this.priceBeforeTax = priceBeforeTax;
    }

    @Column(name = "price_of_tax")
    public double getPriceOfTax() {
        return priceOfTax;
    }

    public void setPriceOfTax(double priceOfTax) {
        this.priceOfTax = priceOfTax;
        updateTotalAmount();
    }

    @Column(name = "money_before_tax")
    public double getMoneyBeforeTax() {
        return moneyBeforeTax;
    }

    public void setMoneyBeforeTax(double moneyBeforeTax) {
        this.moneyBeforeTax = moneyBeforeTax;
    }

    @Column(name = "money_of_tax", nullable = false)
    public double getMoneyOfTax() {
        return moneyOfTax;
    }

    public void setMoneyOfTax(double moneyOfTax) {
        this.moneyOfTax = moneyOfTax;
    }

    @Column(name = "money_after_tax", nullable = false)
    public double getMoneyAfterTax() {
        return moneyAfterTax;
    }

    public void setMoneyAfterTax(double moneyAfterTax) {
        this.moneyAfterTax = moneyAfterTax;
    }

    @Column(name = "currency", nullable = false)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "detail_invoice_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public DetailInvoiceType getType() {
        return type;
    }

    public void setType(DetailInvoiceType type) {
        this.type = type;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public DetailInvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(DetailInvoiceStatus status) {
        this.status = status;
    }

}

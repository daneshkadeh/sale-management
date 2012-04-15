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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "s_detail_invoice")
public class DetailInvoice extends AbstractIdOLObject {
    private Invoice invoice;
    private Product product;
    private Item item;
    private SPackage pack;
    private PackageLine packageLine;
    private DetailInvoice parent; // a package is parent detailInvoice, each line is a sub detailInvoice
    private Set<DetailInvoice> subs = new HashSet<>();
    private Integer amount = 0;

    // TODO: don't know why we have a lot of properties for price?
    private Money priceBeforeTax = Money.zero("VND");
    private Money priceOfTax = Money.zero("VND");
    private Money priceAfterTax = Money.zero("VND");
    private Money moneyBeforeTax = Money.zero("VND");
    private Money moneyOfTax = Money.zero("VND");
    private Money moneyAfterTax = Money.zero("VND"); // is this totalAmount?
    private DetailInvoiceType type = DetailInvoiceType.SALES;
    private DetailInvoiceStatus status = DetailInvoiceStatus.OPEN;

    // Transient field
    @Transient
    private Money totalAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Transient
    public Money getTotalAmount() {
        return totalAmount;
    }

    // Please remove this set method because it has no meaning and can be raise potential bugs (its value is calculated
    // from the other). Remember to set editable is false if using this field in List view
    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void updateTotalAmount() {
        totalAmount = Money.multiply(amount, priceAfterTax);
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "invoice_id")
    @NotNull
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id")
    public SPackage getPackage() {
        return pack;
    }

    public void setPackage(SPackage pack) {
        this.pack = pack;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "packageline_id")
    public PackageLine getPackageLine() {
        return packageLine;
    }

    public void setPackageLine(PackageLine packageLine) {
        this.packageLine = packageLine;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    public DetailInvoice getParent() {
        return parent;
    }

    public void setParent(DetailInvoice parent) {
        this.parent = parent;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<DetailInvoice> getSubs() {
        return subs;
    }

    public void setSubs(Set<DetailInvoice> subs) {
        this.subs = subs;
    }

    @Column(name = "amount", nullable = false)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
        updateTotalAmount();
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_after_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_price_after_tax")) })
    public Money getPriceAfterTax() {
        return priceAfterTax;
    }

    public void setPriceAfterTax(Money priceAfterTax) {
        this.priceAfterTax = priceAfterTax;
        updateTotalAmount();
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_before_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_price_before_tax")) })
    public Money getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setPriceBeforeTax(Money priceBeforeTax) {
        this.priceBeforeTax = priceBeforeTax;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_of_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_price_of_tax")) })
    public Money getPriceOfTax() {
        return priceOfTax;
    }

    public void setPriceOfTax(Money priceOfTax) {
        this.priceOfTax = priceOfTax;
        updateTotalAmount();
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_before_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_money_before_tax")) })
    public Money getMoneyBeforeTax() {
        return moneyBeforeTax;
    }

    public void setMoneyBeforeTax(Money moneyBeforeTax) {
        this.moneyBeforeTax = moneyBeforeTax;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_of_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_money_of_tax")) })
    public Money getMoneyOfTax() {
        return moneyOfTax;
    }

    public void setMoneyOfTax(Money moneyOfTax) {
        this.moneyOfTax = moneyOfTax;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_after_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_money_after_tax")) })
    public Money getMoneyAfterTax() {
        return moneyAfterTax;
    }

    public void setMoneyAfterTax(Money moneyAfterTax) {
        this.moneyAfterTax = moneyAfterTax;
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

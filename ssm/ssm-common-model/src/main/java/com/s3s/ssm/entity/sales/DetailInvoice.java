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

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "s_detail_invoice")
public class DetailInvoice extends AbstractIdOLObject {
    private static final long serialVersionUID = 2668714126906577378L;
    private Invoice invoice;
    private Product product;
    private Item item;
    private SPackage pack;
    private PackageLine packageLine;
    private DetailInvoice parent; // a package is parent detailInvoice, each line is a sub detailInvoice
    private Set<DetailInvoice> subs = new HashSet<>();
    private UnitOfMeasure uom;
    private UnitOfMeasure baseUom;
    private Integer amount = 0;

    // TODO: don't know why we have a lot of properties for price?
    private Money basePrice = Money.zero(CurrencyEnum.VND);
    private Money reducedMoney = Money.zero(CurrencyEnum.VND);
    private Money priceBeforeTax = Money.zero(CurrencyEnum.VND); // = basePrice - reducedMoney
    private Money priceOfTax = Money.zero(CurrencyEnum.VND);
    private Money priceAfterTax = Money.zero(CurrencyEnum.VND);
    private Money moneyBeforeTax = Money.zero(CurrencyEnum.VND);
    private Money moneyOfTax = Money.zero(CurrencyEnum.VND);
    // totalAmount of the detailInvoice = amount * priceAfterTax
    private Money moneyAfterTax = Money.zero(CurrencyEnum.VND);
    private DetailInvoiceType type = DetailInvoiceType.SALES;
    private DetailInvoiceStatus status = DetailInvoiceStatus.OPEN;

    // Transient field
    // @Transient
    // private Money totalAmount = moneyAfterTax;

    // Product name is get from product by default.
    private String productName;

    @ManyToOne(fetch = FetchType.EAGER)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Transient
    public Money getTotalAmount() {
        return moneyAfterTax;
    }

    // TODO: Because of hitory problem, I must keep this transient value. Will be removed when stablize project.
    @Transient
    public void setTotalAmount(Money totalAmount) {
        this.moneyAfterTax = totalAmount;
    }

    private void updateTotalAmount() {
        priceAfterTax = basePrice.minus(reducedMoney);
        moneyAfterTax = Money.multiply(amount, priceAfterTax);
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
    @Cascade(value = { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.DELETE })
    public Set<DetailInvoice> getSubs() {
        return subs;
    }

    public void setSubs(Set<DetailInvoice> subs) {
        this.subs = subs;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_id")
    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_uom_id")
    public UnitOfMeasure getBaseUom() {
        return baseUom;
    }

    public void setBaseUom(UnitOfMeasure baseUom) {
        this.baseUom = baseUom;
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

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "base_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_base_price")) })
    public Money getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Money basePrice) {
        this.basePrice = basePrice;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "reduced_money")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_reduced_money")) })
    public Money getReducedMoney() {
        return reducedMoney;
    }

    public void setReducedMoney(Money reducedMoney) {
        this.reducedMoney = reducedMoney;
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

    @NotNull
    @Column(name = "detail_invoice_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public DetailInvoiceType getType() {
        return type;
    }

    public void setType(DetailInvoiceType type) {
        this.type = type;
    }

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public DetailInvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(DetailInvoiceStatus status) {
        this.status = status;
    }

    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public DetailInvoice duplicate() {
        DetailInvoice detailInvoice = new DetailInvoice();
        try {
            BeanUtils.copyProperties(detailInvoice, this);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: Can not copy detailInvoice!");
        }
        return detailInvoice;
    }
}

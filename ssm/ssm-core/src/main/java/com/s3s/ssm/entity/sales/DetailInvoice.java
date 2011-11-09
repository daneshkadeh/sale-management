package com.s3s.ssm.entity.sales;

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
import com.s3s.ssm.entity.param.CurrencyEnum;
import com.s3s.ssm.entity.param.Item;

@Entity
@Table(name = "s_detail_invoice")
public class DetailInvoice extends AbstractIdOLObject {
    private Invoice invoice;
    private Item item;
    private Integer amount;
    private Double priceBeforeTax;
    private Double priceOfTax;
    private Double priceAfterTax;
    private Double moneyBeforeTax;
    private Double moneyOfTax;
    private Double moneyAfterTax;
    private CurrencyEnum currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id", nullable = false)
    @NotNull
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

    @Column(name = "amount", nullable = false)
    @NotNull
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
    }

    @Column(name = "price_after_tax", nullable = false)
    @NotNull
    public Double getPriceAfterTax() {
        return priceAfterTax;
    }

    public void setPriceAfterTax(Double priceAfterTax) {
        this.priceAfterTax = priceAfterTax;
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
    @Enumerated(EnumType.STRING)
    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

}

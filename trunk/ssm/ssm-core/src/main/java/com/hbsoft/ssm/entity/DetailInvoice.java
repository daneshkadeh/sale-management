package com.hbsoft.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_detail_invoice")
public class DetailInvoice extends BaseIdObject {
    private Integer goodsId;
    private String goodsName;
    private Integer quantity;
    private Double priceBeforeTax;
    private Double tax;
    private Double priceAfterTax;
    private Double moneyBeforeTax;
    private Double moneyOfTax;
    private Double moneyAfterTax;
    private Integer invoiceId;

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Column(name = "goods_id", nullable = false)
    @NotNull
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Column(name = "goods_name", nullable = false)
    @NotNull
    public String getGoodsName() {
        return goodsName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "quantity", nullable = false)
    @NotNull
    public Integer getQuantity() {
        return quantity;
    }

    public void setPriceBeforeTax(Double priceBeforeTax) {
        this.priceBeforeTax = priceBeforeTax;
    }

    @Column(name = "price_before_tax", nullable = false)
    @NotNull
    public Double getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Column(name = "tax", nullable = false)
    @NotNull
    public Double getTax() {
        return tax;
    }

    public void setPriceAfterTax(Double priceAfterTax) {
        this.priceAfterTax = priceAfterTax;
    }

    @Column(name = "price_after_tax", nullable = false)
    @NotNull
    public Double getPriceAfterTax() {
        return priceAfterTax;
    }

    public void setMoneyBeforeTax(Double moneyBeforeTax) {
        this.moneyBeforeTax = moneyBeforeTax;
    }

    @Column(name = "money_before_tax", nullable = false)
    @NotNull
    public Double getMoneyBeforeTax() {
        return moneyBeforeTax;
    }

    public void setMoneyOfTax(Double moneyOfTax) {
        this.moneyOfTax = moneyOfTax;
    }

    @Column(name = "money_of_tax", nullable = false)
    @NotNull
    public Double getMoneyOfTax() {
        return moneyOfTax;
    }

    public void setMoneyAfterTax(Double moneyAfterTax) {
        this.moneyAfterTax = moneyAfterTax;
    }

    @Column(name = "money_after_tax", nullable = false)
    @NotNull
    public Double getMoneyAfterTax() {
        return moneyAfterTax;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Column(name = "invoice_id", nullable = false)
    @NotNull
    public Integer getInvoiceId() {
        return invoiceId;
    }

}

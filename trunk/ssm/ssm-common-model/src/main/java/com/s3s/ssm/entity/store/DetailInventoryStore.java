/*
 * DetailImportProduct
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
package com.s3s.ssm.entity.store;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "store_detail_inventory")
public class DetailInventoryStore extends AbstractIdOLObject {
    private static final long serialVersionUID = -6106647280533540331L;
    private Integer lineNo;
    private InventoryStoreForm inventoryForm;
    private Product product = new Product();
    private Item item = new Item();
    private UnitOfMeasure baseUom;
    private Integer curQty = 0;
    private Integer realQty = 0;
    private Integer lostQty = 0;
    private Money priceUnit = Money.create(CurrencyEnum.VND, 0L);
    private Money curPriceSubtotal = Money.create(CurrencyEnum.VND, 0L);
    private Money realPriceSubtotal = Money.create(CurrencyEnum.VND, 0L);

    @Column(name = "line_no")
    @DecimalMin(value = "1")
    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @NotNull
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "form_id")
    @NotNull
    public InventoryStoreForm getInventoryForm() {
        return inventoryForm;
    }

    public void setInventoryForm(InventoryStoreForm inventoryForm) {
        this.inventoryForm = inventoryForm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_uom_id")
    @NotNull
    public UnitOfMeasure getBaseUom() {
        return baseUom;
    }

    public void setBaseUom(UnitOfMeasure baseUom) {
        this.baseUom = baseUom;
    }

    @Column(name = "cur_qty")
    public Integer getCurQty() {
        return curQty;
    }

    public void setCurQty(Integer curQty) {
        this.curQty = curQty;
    }

    @Column(name = "real_qty")
    public Integer getRealQty() {
        return realQty;
    }

    public void setRealQty(Integer realQty) {
        this.realQty = realQty;
    }

    @Column(name = "lost_qty")
    public Integer getLostQty() {
        return lostQty;
    }

    public void setLostQty(Integer lostQty) {
        this.lostQty = lostQty;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "cur_price_subtotal")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code1")) })
    @NotNull
    public Money getCurPriceSubtotal() {
        return curPriceSubtotal;
    }

    public void setCurPriceSubtotal(Money curPriceSubtotal) {
        this.curPriceSubtotal = curPriceSubtotal;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "real_price_subtotal")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code2")) })
    @NotNull
    public Money getRealPriceSubtotal() {
        return realPriceSubtotal;
    }

    public void setRealPriceSubtotal(Money realPriceSubtotal) {
        this.realPriceSubtotal = realPriceSubtotal;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_unit")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code3")) })
    @NotNull
    public Money getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Money priceUnit) {
        this.priceUnit = priceUnit;
    }
}
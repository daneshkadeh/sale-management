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

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "store_detail_import")
public class DetailImportStore extends AbstractIdOLObject {
    private static final long serialVersionUID = 193415641537228770L;
    private Integer lineNo;
    private ImportStoreForm importStoreForm;
    private Product product;
    private String productName;
    private Item item;
    private UnitOfMeasure uom;
    private UnitOfMeasure baseUom;
    private Integer quantity = 0;
    private Money priceUnit = Money.create(CurrencyEnum.VND, 0L);;
    private Money priceSubtotal = Money.create(CurrencyEnum.VND, 0L);;

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

    @Column(name = "product_name")
    @NotBlank
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "form_id")
    @NotNull
    public ImportStoreForm getImportStoreForm() {
        return importStoreForm;
    }

    public void setImportStoreForm(ImportStoreForm importStoreForm) {
        this.importStoreForm = importStoreForm;
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
    @JoinColumn(name = "uom_id")
    @NotNull
    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
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

    @Column(name = "qty")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_unit")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    @NotNull
    public Money getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Money priceUnit) {
        this.priceUnit = priceUnit;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_subtotal")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code1")) })
    public Money getPriceSubtotal() {
        return priceSubtotal;
    }

    public void setPriceSubtotal(Money priceSubtotal) {
        this.priceSubtotal = priceSubtotal;
    }
}
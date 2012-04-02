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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "store_detail_import")
public class DetailImportStore extends AbstractIdOLObject {
    private static final long serialVersionUID = 193415641537228770L;
    private ImportStoreForm importStoreForm;
    private Item item;
    private Integer quantity = 0;
    private Money priceUnit;
    private Money priceSubtotal;

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

    @Column(name = "qty")
    @Min(value = 0)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_unit")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Money priceUnit) {
        this.priceUnit = priceUnit;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price_subtotal")) })
    public Money getPriceSubtotal() {
        return priceSubtotal;
    }

    public void setPriceSubtotal(Money priceSubtotal) {
        this.priceSubtotal = priceSubtotal;
    }
}
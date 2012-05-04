/*
 * Item
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
package com.s3s.ssm.entity.catalog;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.Money;

/**
 * Item code is the barcode to identify the product.
 * 
 */
@Entity
@Table(name = "ca_item")
public class Item extends AbstractCodeOLObject {
    private Product product;
    private String sumUomName;
    private UnitOfMeasure uom;
    private Set<ItemPropertyValue> listPropertyValue = new HashSet<>();
    private Set<ItemPrice> listItemPrices = new HashSet<>();

    private Money baseSellPrice;
    private Money originPrice;

    // private ItemOriginPrice mainOriginPrice;

    // @ManyToOne
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "sum_uom_name", length = 128)
    public String getSumUomName() {
        return sumUomName;
    }

    public void setSumUomName(String sumUomName) {
        this.sumUomName = sumUomName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "item")
    public Set<ItemPropertyValue> getListPropertyValue() {
        return listPropertyValue;
    }

    public void setListPropertyValue(Set<ItemPropertyValue> listPropertyValue) {
        this.listPropertyValue = listPropertyValue;
    }

    public ItemPropertyValue getPropertyValue(ProductProperty property) {
        for (ItemPropertyValue value : listPropertyValue) {
            if (value.getProperty().equals(property)) {
                return value;
            }
        }
        return null;
    }

    public void addPropertyValue(ItemPropertyValue propertyValue) {
        propertyValue.setItem(this);
        listPropertyValue.add(propertyValue);
    }

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "item")
    public Set<ItemPrice> getListItemPrices() {
        return listItemPrices;
    }

    public void setListItemPrices(Set<ItemPrice> listItemPrices) {
        this.listItemPrices = listItemPrices;
    }

    // @OneToOne(mappedBy = "item")
    // public ItemOriginPrice getMainOriginPrice() {
    // return mainOriginPrice;
    // }
    //
    // public void setMainOriginPrice(ItemOriginPrice mainOriginPrice) {
    // this.mainOriginPrice = mainOriginPrice;
    // }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "base_sell_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_sell")) })
    public Money getBaseSellPrice() {
        return baseSellPrice;
    }

    public void setBaseSellPrice(Money baseSellPrice) {
        this.baseSellPrice = baseSellPrice;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "origin_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_origin")) })
    public Money getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Money originPrice) {
        this.originPrice = originPrice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_id")
    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }

}

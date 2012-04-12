/*
 * DetailSalesContract
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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "s_detail_sales_contract")
public class DetailSalesContract extends AbstractIdOLObject {
    private SalesContract salesContract;
    private Item item;
    private Integer quantity;
    private Money unitPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salescon_id", nullable = false)
    public SalesContract getSalesContract() {
        return salesContract;
    }

    public void setSalesContract(SalesContract salesContract) {
        this.salesContract = salesContract;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "quantity", nullable = false)
    @NotNull
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "unit_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    @NotNull
    public Money getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

}

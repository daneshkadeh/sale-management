/*
 * ShipmentPriceDetail
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Berg�re 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.entity.store;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.Money;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "store_ship_price")
public class ShipPrice extends AbstractIdOLObject {
    private ShipPriceType shipPriceType;
    private Money price;
    private Date updateDate = new Date();

    @ManyToOne
    @JoinColumn(name = "ship_price_type_id")
    public ShipPriceType getShipPriceType() {
        return shipPriceType;
    }

    public void setShipPriceType(ShipPriceType shipPriceType) {
        this.shipPriceType = shipPriceType;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    @Column(name = "update_date", nullable = false)
    @NotNull
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

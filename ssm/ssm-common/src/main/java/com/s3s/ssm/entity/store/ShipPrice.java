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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "s_ship_date_price")
public class ShipPrice extends AbstractIdOLObject {
    private ShipPriceType shipPrice;
    private Double price = 0.0;
    private Date updateDate = new Date();

    @ManyToOne
    @JoinColumn(name = "ship_price_id")
    public ShipPriceType getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(ShipPriceType shipPrice) {
        this.shipPrice = shipPrice;
    }

    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "update_date", nullable = false)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

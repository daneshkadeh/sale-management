/*
 * ShipmentType
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
package com.s3s.ssm.entity.shipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "ship_shipment_type")
public class ShipmentType extends AbstractActiveCodeOLObject {
    private static final long serialVersionUID = -7071329368705780533L;
    private String name;
    private Double basePrice;
    private String currency;

    @Column(name = "name")
    @Max(value = 256)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "base_price")
    @NotNull
    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    @Column(name = "currency")
    @NotNull
    @Enumerated(EnumType.STRING)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

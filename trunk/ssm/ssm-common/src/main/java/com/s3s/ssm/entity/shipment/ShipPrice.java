/*
 * ShipmentPrice
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

package com.s3s.ssm.entity.shipment;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "s_ship_price")
public class ShipPrice extends AbstractCodeOLObject {
    private String name;
    private Set<ShipDatePrice> shipPriceDetails = new HashSet<ShipDatePrice>();

    @Column(name = "name", length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "shipPrice")
    public Set<ShipDatePrice> getShipPriceDetails() {
        return shipPriceDetails;
    }

    public void setShipPriceDetails(Set<ShipDatePrice> shipPriceDetails) {
        this.shipPriceDetails = shipPriceDetails;
    }

    @Override
    public String toString() {
        return name;
    }
}

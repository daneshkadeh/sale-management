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

package com.s3s.ssm.entity.store;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractCodeOLObject;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "s_ship_price_type")
public class ShipPriceType extends AbstractCodeOLObject {
    private String name;
    private Set<ShipPrice> shipPrices = new HashSet<ShipPrice>();

    @Column(name = "name", length = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "shipPrice", fetch = FetchType.LAZY)
    public Set<ShipPrice> getShipPrices() {
        return shipPrices;
    }

    public void setShipPrices(Set<ShipPrice> shipPrices) {
        this.shipPrices = shipPrices;
    }

    @Override
    public String toString() {
        return name;
    }
}

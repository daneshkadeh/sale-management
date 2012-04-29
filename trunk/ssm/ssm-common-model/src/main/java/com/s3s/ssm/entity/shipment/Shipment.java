/*
 * Shipment
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.s3s.ssm.entity.store.ExportStoreForm;

@Entity
@Table(name = "ship_shipment")
public class Shipment extends AbstractActiveCodeOLObject {
    private ShipmentType shipmentType;
    private ExportStoreForm exportStoreForm;
    private Double money;
    private String currency;
    private ShipmentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_type_id")
    @NotNull
    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exportstore_id")
    @NotNull
    public ExportStoreForm getExportStoreForm() {
        return exportStoreForm;
    }

    public void setExportStoreForm(ExportStoreForm exportStoreForm) {
        this.exportStoreForm = exportStoreForm;
    }

    @Column(name = "money")
    @NotNull
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

}

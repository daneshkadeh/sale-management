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

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.config.CurrencyEnum;
import com.s3s.ssm.entity.store.ExportStoreForm;

@Entity
@Table(name = "s_shipment")
public class Shipment extends AbstractIdOLObject {
    private ShipmentType shipmentType;
    private ExportStoreForm exportStoreForm;
    private Double money;
    private CurrencyEnum currency;
    private ShipmentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_type_id", nullable = false)
    @NotNull
    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exportstore_id", nullable = false)
    @NotNull
    public ExportStoreForm getExportStoreForm() {
        return exportStoreForm;
    }

    public void setExportStoreForm(ExportStoreForm exportStoreForm) {
        this.exportStoreForm = exportStoreForm;
    }

    @Column(name = "money", nullable = false)
    @NotNull
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Column(name = "currency", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

}
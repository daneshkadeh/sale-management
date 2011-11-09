package com.s3s.ssm.entity.shipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.param.CurrencyEnum;

@Entity
@Table(name = "s_shipment_type")
public class ShipmentType extends AbstractCodeOLObject {
    private String name;
    private Double basePrice;
    private CurrencyEnum currency;
    private Boolean active;

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "base_price", nullable = false)
    @NotNull
    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
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

    @Column(name = "active", nullable = false)
    @NotNull
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

/*
 * UnitOfMeasure
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
package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

/**
 * UnitOfMeasure is used to measure the quantity of product. Eg. Kilogam, gam, met, centimet, cap, doi, cai, bich
 * 
 */
@Entity
@Table(name = "config_unit_of_measure")
public class UnitOfMeasure extends AbstractCodeOLObject {
    private UomCategory uomCategory;
    private String name;
    private Float exchangeRate = 1F;
    private Boolean isBaseMeasure = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_category_id")
    @NotNull
    public UomCategory getUomCategory() {
        return uomCategory;
    }

    public void setUomCategory(UomCategory uomCategory) {
        this.uomCategory = uomCategory;
    }

    @Column(name = "uom_name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "change_rate")
    @NotNull
    public Float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Float changeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Column(name = "is_base_measure", nullable = false)
    public Boolean getIsBaseMeasure() {
        return isBaseMeasure;
    }

    public void setIsBaseMeasure(Boolean isBaseMeasure) {
        this.isBaseMeasure = isBaseMeasure;
    }
}

/*
 * SPackage
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
package com.s3s.ssm.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "ca_package")
public class SPackage extends AbstractCodeOLObject {
    private String name;
    private Integer minTotalItemAmount;
    private Integer maxTotalItemAmount;

    @Column(name = "name", length = 128, nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "min_total_item_amount", nullable = false)
    @NotNull
    public Integer getMinTotalItemAmount() {
        return minTotalItemAmount;
    }

    public void setMinTotalItemAmount(Integer minTotalItemAmount) {
        this.minTotalItemAmount = minTotalItemAmount;
    }

    @Column(name = "max_total_item_amount", nullable = false)
    @NotNull
    public Integer getMaxTotalItemAmount() {
        return maxTotalItemAmount;
    }

    public void setMaxTotalItemAmount(Integer maxTotalItemAmount) {
        this.maxTotalItemAmount = maxTotalItemAmount;
    }
}

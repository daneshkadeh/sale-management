/*
 * GoodsTest
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
package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_goods")
public class GoodsTest extends AbstractBaseIdObject {
    private String name;
    private Double priceBeforeTax;
    private Double tax;
    private Double priceAfterTax;
    private Integer addQuantity;
    private Integer curQuantity;

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setPriceBeforeTax(Double priceBeforeTax) {
        this.priceBeforeTax = priceBeforeTax;
    }

    @Column(name = "priceBeforeTax", nullable = false)
    @NotNull
    public Double getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Column(name = "tax", nullable = false)
    @NotNull
    public Double getTax() {
        return tax;
    }

    public void setPriceAfterTax(Double priceAfterTax) {
        this.priceAfterTax = priceAfterTax;
    }

    @Column(name = "price_after_tax", nullable = false)
    @NotNull
    public Double getPriceAfterTax() {
        return priceAfterTax;
    }

    public void setAddQuantity(Integer addQuantity) {
        this.addQuantity = addQuantity;
    }

    @Column(name = "addQuan", nullable = false)
    @NotNull
    public Integer getAddQuantity() {
        return addQuantity;
    }

    public void setCurQuantity(Integer curQuantity) {
        this.curQuantity = curQuantity;
    }

    @Column(name = "curQuan", nullable = false)
    @NotNull
    public Integer getCurQuantity() {
        return curQuantity;
    }
}

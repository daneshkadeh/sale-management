/*
 * ProductType
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_product_type")
public class ProductType extends AbstractCodeOLObject {
    private ProductFamilyType productFamilyType;
    private String name;

    @Column(name = "product_family_type", nullable = false, length = 32)
    @NotNull
    @Enumerated(EnumType.STRING)
    public ProductFamilyType getProductFamilyType() {
        return productFamilyType;
    }

    public void setProductFamilyType(ProductFamilyType productFamilyType) {
        this.productFamilyType = productFamilyType;
    }

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

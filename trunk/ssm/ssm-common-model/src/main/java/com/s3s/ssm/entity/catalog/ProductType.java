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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.s3s.ssm.entity.config.UomCategory;

@Entity
@Table(name = "ca_product_type")
public class ProductType extends AbstractActiveCodeOLObject {
    private ProductFamilyType productFamilyType;
    private String name;
    private ProductType parent;
    private UomCategory uomCategory;

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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public ProductType getParent() {
        return parent;
    }

    public void setParent(ProductType parent) {
        this.parent = parent;
    }

    @ManyToOne
    @JoinColumn(name = "uom_category_id")
    @NotNull
    public UomCategory getUomCategory() {
        return uomCategory;
    }

    public void setUomCategory(UomCategory uomCategory) {
        this.uomCategory = uomCategory;
    }

}

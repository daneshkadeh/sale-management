/*
 * ProductProperty
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * Property of a product. Eg. Product T-SHIRT has following properties: COLOR, SIZE
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "ca_product_property")
public class ProductProperty extends AbstractCodeOLObject {
    private String name;
    private PropertyType type;
    private ProductFamilyType productFamilyType = ProductFamilyType.GOODS;
    private List<ProductPropertyElement> elements = new ArrayList<>();

    public enum PropertyType {
        LIST, SIMPLE;
        @Override
        public String toString() {
            return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
        }
    }

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "property_type", nullable = false, length = 32)
    @NotNull
    @Enumerated(EnumType.STRING)
    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "property")
    public List<ProductPropertyElement> getElements() {
        return elements;
    }

    public void setElements(List<ProductPropertyElement> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return name;
    }

    @Column(name = "product_family_type", nullable = false, length = 32)
    @NotNull
    @Enumerated(EnumType.STRING)
    public ProductFamilyType getProductFamilyType() {
        return productFamilyType;
    }

    public void setProductFamilyType(ProductFamilyType productFamilyType) {
        this.productFamilyType = productFamilyType;
    }
}

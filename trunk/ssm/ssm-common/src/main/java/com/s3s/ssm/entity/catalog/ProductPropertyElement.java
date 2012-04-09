/*
 * ProductPropertyElement
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * An element of a property. Eg. property COLOR has following elements: GREEN, BLUE, RED, YELLOW
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "ca_productproperty_element")
public class ProductPropertyElement extends AbstractIdOLObject {
    private ProductProperty property;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    public ProductProperty getProperty() {
        return property;
    }

    public void setProperty(ProductProperty property) {
        this.property = property;
    }

    @Column(name = "element_value", nullable = false, length = 128)
    @NotNull
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

/*
 * ItemPropertyValue
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

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * A value links to a product. Either element or value must be defined.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_itemproperty_value")
public class ItemPropertyValue extends AbstractIdOLObject {
    private Item item;
    private ProductProperty property;
    private ProductPropertyElement element;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    public ProductProperty getProperty() {
        return property;
    }

    public void setProperty(ProductProperty property) {
        this.property = property;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "element_id")
    public ProductPropertyElement getElement() {
        return element;
    }

    public void setElement(ProductPropertyElement element) {
        this.element = element;
    }

    @Column(name = "element_value", length = 128)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

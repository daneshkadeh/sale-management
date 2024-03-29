/*
 * GroupDetailImportData
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

package com.s3s.ssm.dto.store;

import java.io.Serializable;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.config.UnitOfMeasure;

/**
 * TODO Phuc: The class extend {@link AbstractBaseIdObject} to work-around show the list of DTO in list view
 * 
 * @author Phan Hong Phuc
 * @since Apr 30, 2012
 */
public class GroupDetailExportData implements Serializable {
    private static final long serialVersionUID = -2889698358659809510L;
    private Product product;
    private Item item;
    private Integer quantity = 0;
    private UnitOfMeasure uom;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

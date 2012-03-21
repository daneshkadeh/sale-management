/*
 * DetailImportProduct
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
package com.s3s.ssm.entity.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;

@Entity
@Table(name = "s_detail_import_product")
public class DetailImportProduct extends AbstractIdOLObject {
    private ImportProductForm importProductForm;
    private Item item;
    private Integer importAmount;
    private Integer remainingAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "import_product_id", nullable = false)
    @NotNull
    public ImportProductForm getImportProductForm() {
        return importProductForm;
    }

    public void setImportProductForm(ImportProductForm importProductForm) {
        this.importProductForm = importProductForm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "import_amount", nullable = false)
    @NotNull
    public Integer getImportAmount() {
        return importAmount;
    }

    public void setImportAmount(Integer importAmount) {
        this.importAmount = importAmount;
    }

    @Column(name = "remaining_amount", nullable = false)
    @NotNull
    public Integer getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Integer remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}

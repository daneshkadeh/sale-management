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
import java.util.Date;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * @author Le Thanh Hoang
 * @since May 02, 2012
 */
public class ImportStoreReportData extends AbstractIdOLObject implements Serializable {
    private static final long serialVersionUID = 1035013656255140818L;
    private Date importingDate;
    private String salesContractCode;
    private String storeName;
    private String supplierName;
    private String productName;
    private String itemName;
    private String uomName;
    private Integer quantity;

    public Date getImportingDate() {
        return importingDate;
    }

    public void setImportingDate(Date importingDate) {
        this.importingDate = importingDate;
    }

    public String getSalesContractCode() {
        return salesContractCode;
    }

    public void setSalesContractCode(String salesContractCode) {
        this.salesContractCode = salesContractCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private String notes;

}

/*
 * UnsoldProductDTO
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.dto.store;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ProductInStoreDTO {
    private String productCode;
    private String productName;
    private String itemName;
    private String barcode;
    private String uom;
    private Integer firstQty = 0;
    private Integer importQty = 0;
    private Integer exportQty = 0;
    private Integer lastQty = 0;
    private Long priceUnit = 0L;
    private Long priceUnitTotal = 0L;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Integer getFirstQty() {
        return firstQty;
    }

    public void setFirstQty(Integer firstQty) {
        this.firstQty = firstQty;
    }

    public Integer getImportQty() {
        return importQty;
    }

    public void setImportQty(Integer importQty) {
        this.importQty = importQty;
    }

    public Integer getExportQty() {
        return exportQty;
    }

    public void setExportQty(Integer exportQty) {
        this.exportQty = exportQty;
    }

    public Integer getLastQty() {
        return lastQty;
    }

    public void setLastQty(Integer lastQty) {
        this.lastQty = lastQty;
    }

    public Long getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Long priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Long getPriceUnitTotal() {
        return priceUnitTotal;
    }

    public void setPriceUnitTotal(Long priceUnitTotal) {
        this.priceUnitTotal = priceUnitTotal;
    }

}

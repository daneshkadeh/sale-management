/*
 * DetailInvoiceDataBean
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

package com.s3s.ssm.reports.bean;

import com.s3s.ssm.entity.sales.DetailInvoice;

/**
 * @author Phan Hong Phuc
 * @since May 14, 2012
 */
public class DetailInvoiceDataBean {
    private String itemCode;
    private String itemName;
    private int amount;
    private String uomName;
    private long priceBeforeTax;
    private long priceAfterTax;
    private String type;
    private String status;
    private long sumMoney;

    public DetailInvoiceDataBean(DetailInvoice di) {
        itemCode = di.getItem() == null ? "" : di.getItem().getCode();
        itemName = di.getProductName();
        amount = di.getAmount();
        uomName = di.getUom() == null ? "" : di.getUom().getName();
        priceBeforeTax = di.getPriceBeforeTax().getValue();
        priceAfterTax = di.getPriceAfterTax().getValue();
        type = di.getType().toString();
        status = di.getStatus().toString();
        sumMoney = di.getTotalAmount().getValue();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public long getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setPriceBeforeTax(long priceBeforeTax) {
        this.priceBeforeTax = priceBeforeTax;
    }

    public long getPriceAfterTax() {
        return priceAfterTax;
    }

    public void setPriceAfterTax(long priceAfterTax) {
        this.priceAfterTax = priceAfterTax;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(long sumMoney) {
        this.sumMoney = sumMoney;
    }

}

/*
 * ImportProductForm
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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.sales.SalesContract;

@Entity
@Table(name = "s_import_store")
public class ImportStore extends AbstractCodeOLObject {
    private static final long serialVersionUID = 1L;
    private Date createdDate;
    private Store store;
    private SalesContract salesContract;
    private String supplierName;
    private Date receiptDate;
    private Operator receiver;
    private String sender;
    private String mobilizationOrder;
    private String importNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @NotNull
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salescon_id", nullable = false)
    @NotNull
    public SalesContract getSalesContract() {
        return salesContract;
    }

    public void setSalesContract(SalesContract salesContract) {
        this.salesContract = salesContract;
    }

    @Column(name = "supplier_name")
    @NotBlank
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "receipt_date")
    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Column(name = "created_date", nullable = false)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @NotNull
    public Operator getReceiver() {
        return receiver;
    }

    public void setReceiver(Operator receiver) {
        this.receiver = receiver;
    }

    @Column(name = "sender")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "mob_order")
    public String getMobilizationOrder() {
        return mobilizationOrder;
    }

    public void setMobilizationOrder(String mobilizationOrder) {
        this.mobilizationOrder = mobilizationOrder;
    }

    @Column(name = "import_num")
    public String getImportNum() {
        return importNum;
    }

    public void setImportNum(String importNum) {
        this.importNum = importNum;
    }

}

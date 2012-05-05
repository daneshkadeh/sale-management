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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.sales.ImportationSC;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "store_import_form")
public class ImportStoreForm extends AbstractCodeOLObject {
    private static final long serialVersionUID = 1L;
    private Date createdDate = new Date();
    private Date modifiedDate;
    private Store store;
    private SalesContract salesContract;
    private ImportationSC importationSC;
    private Date receiptDate = new Date();
    private Operator receiver;
    private String sender;
    private Boolean isProcessed = false;
    private Boolean isPrinted = false;
    private Boolean printAfterSave = false;
    private ImportStoreStatus status = ImportStoreStatus.NEW;
    private ShipPriceType shipPriceType;
    private Money shipPrice;
    private Double shipNum = 0D;
    private Integer qtyTotal;
    private Money amtTotal;
    private Set<DetailImportStore> detailImportStores = new HashSet<DetailImportStore>();

    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modified_date")
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    @NotNull
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salescon_id", nullable = false)
    @NotNull
    public SalesContract getSalesContract() {
        return salesContract;
    }

    public void setSalesContract(SalesContract salesContract) {
        this.salesContract = salesContract;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "importationSC_id")
    public ImportationSC getImportationSC() {
        return importationSC;
    }

    public void setImportationSC(ImportationSC importationSC) {
        this.importationSC = importationSC;
    }

    @Column(name = "receipt_date")
    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
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

    @Column(name = "is_processed")
    public Boolean getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    @Column(name = "is_printed")
    public Boolean getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(Boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    @Column(name = "print_after_save")
    public Boolean getPrintAfterSave() {
        return printAfterSave;
    }

    public void setPrintAfterSave(Boolean printAfterSave) {
        this.printAfterSave = printAfterSave;
    }

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "importStoreForm", fetch = FetchType.EAGER)
    // @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
            public
            Set<DetailImportStore> getDetailImportStores() {
        return detailImportStores;
    }

    public void setDetailImportStores(Set<DetailImportStore> detailImportStores) {
        this.detailImportStores = detailImportStores;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public ImportStoreStatus getStatus() {
        return status;
    }

    public void setStatus(ImportStoreStatus status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "shipPriceType_id")
    public ShipPriceType getShipPriceType() {
        return shipPriceType;
    }

    public void setShipPriceType(ShipPriceType shipPriceType) {
        this.shipPriceType = shipPriceType;
    }

    @Column(name = "ship_num")
    public Double getShipNum() {
        return shipNum;
    }

    public void setShipNum(Double shipNum) {
        this.shipNum = shipNum;
    }

    @Column(name = "quantity_total")
    public Integer getQtyTotal() {
        return qtyTotal;
    }

    public void setQtyTotal(Integer qtyTotal) {
        this.qtyTotal = qtyTotal;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "amount_total")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getAmtTotal() {
        return amtTotal;
    }

    public void setAmtTotal(Money amtTotal) {
        this.amtTotal = amtTotal;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "ship_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code2")) })
    public Money getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(Money shipPrice) {
        this.shipPrice = shipPrice;
    }
}

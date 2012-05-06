/*
 * ExportStoreForm
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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.shipment.TransportationType;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "store_export_form")
public class ExportStoreForm extends AbstractCodeOLObject {
    private static final long serialVersionUID = 1039871097966379377L;
    private Date createdDate = new Date();
    private Date modifiedDate;
    private Store store;
    private Operator staff;
    private Invoice invoice;
    private String custCode;
    private String custName;
    private TransportationType transType;
    private Money transPrice;
    private Boolean isPrinted = false;
    private Boolean printAfterSave = false;
    private Long reqQuanTotal = 0L;
    private Long realQuanTotal = 0L;
    private Long remainQuanTotal = 0L;
    private ExportStoreStatus status = ExportStoreStatus.NEW;

    private Set<DetailExportStore> exportDetails = new HashSet<DetailExportStore>();

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE })
    @JoinColumn(name = "invoice_id")
    @NotNull
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Column(name = "cust_code")
    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    @Column(name = "cust_name")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trans_type_id")
    @NotNull
    public TransportationType getTransType() {
        return transType;
    }

    public void setTransType(TransportationType transType) {
        this.transType = transType;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "trans_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getTransPrice() {
        return transPrice;
    }

    public void setTransPrice(Money transPrice) {
        this.transPrice = transPrice;
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

    @Column(name = "req_quan_total")
    @DecimalMin(value = "0")
    public Long getReqQuanTotal() {
        return reqQuanTotal;
    }

    public void setReqQuanTotal(Long reqQuanTotal) {
        this.reqQuanTotal = reqQuanTotal;
    }

    @Column(name = "real_quan_total")
    @DecimalMin(value = "0")
    public Long getRealQuanTotal() {
        return realQuanTotal;
    }

    public void setRealQuanTotal(Long realQuanTotal) {
        this.realQuanTotal = realQuanTotal;
    }

    @Column(name = "remain_quan_total")
    @DecimalMin(value = "0")
    public Long getRemainQuanTotal() {
        return remainQuanTotal;
    }

    public void setRemainQuanTotal(Long remainQuanTotal) {
        this.remainQuanTotal = remainQuanTotal;
    }

    @Column(name = "created_date")
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
    @JoinColumn(name = "store_id")
    @NotNull
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    @NotNull
    public Operator getStaff() {
        return staff;
    }

    public void setStaff(Operator staff) {
        this.staff = staff;
    }

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    public ExportStoreStatus getStatus() {
        return status;
    }

    public void setStatus(ExportStoreStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "exportForm", fetch = FetchType.EAGER)
    @Cascade(value = { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.DELETE })
    public Set<DetailExportStore> getExportDetails() {
        return exportDetails;
    }

    public void setExportDetails(Set<DetailExportStore> exportDetails) {
        this.exportDetails = exportDetails;
    }
}

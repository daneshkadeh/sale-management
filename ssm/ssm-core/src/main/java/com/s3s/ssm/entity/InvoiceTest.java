/*
 * InvoiceTest
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
package com.s3s.ssm.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_invoice")
public class InvoiceTest extends AbstractBaseIdObject {
    private Date createdDate;
    private Integer customerId;
    private Double totalBeforeTax;
    private Double taxTotal;
    private Double totalAfterTax;
    private List<DetailInvoiceTest> detailInvoices = new ArrayList<>();

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "created_date", nullable = false)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name = "customer_id", nullable = false)
    @NotNull
    public Integer getCustomerId() {
        return customerId;
    }

    public void setTotalBeforeTax(Double totalBeforeTax) {
        this.totalBeforeTax = totalBeforeTax;
    }

    @Column(name = "total_before_tax", nullable = false)
    @NotNull
    public Double getTotalBeforeTax() {
        return totalBeforeTax;
    }

    public void setTaxTotal(Double taxTotal) {
        this.taxTotal = taxTotal;
    }

    @Column(name = "tax_total", nullable = false)
    @NotNull
    public Double getTaxTotal() {
        return taxTotal;
    }

    public void setTotalAfterTax(Double totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    @Column(name = "total_after_tax", nullable = false)
    @NotNull
    public Double getTotalAfterTax() {
        return totalAfterTax;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice", fetch = FetchType.EAGER)
    public List<DetailInvoiceTest> getDetailInvoices() {
        return detailInvoices;
    }

    public void setDetailInvoices(List<DetailInvoiceTest> detailInvoices) {
        this.detailInvoices = detailInvoices;
    }

    public void addDetailInvoice(DetailInvoiceTest detailInvoice) {
        detailInvoice.setInvoice(this);
        this.getDetailInvoices().add(detailInvoice);
    }

}

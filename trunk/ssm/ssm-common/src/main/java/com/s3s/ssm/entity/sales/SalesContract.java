/*
 * SalesContract
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
package com.s3s.ssm.entity.sales;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.contact.Partner;

@Entity
@Table(name = "s_sales_contract")
public class SalesContract extends AbstractCodeOLObject {
    private Partner supplier;
    private Date dateContract;
    private Double moneyBeforeTax;
    private Double moneyOfTax;
    private Double moneyAfterTax;
    private String currency;
    private SalesContractStatus status = SalesContractStatus.OPEN;
    private Set<DetailSalesContract> detailSalesContracts = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    public Partner getSupplier() {
        return supplier;
    }

    public void setSupplier(Partner supplier) {
        this.supplier = supplier;
    }

    @Column(name = "datetime_contract", nullable = false)
    @NotNull
    public Date getDateContract() {
        return dateContract;
    }

    public void setDateContract(Date dateContract) {
        this.dateContract = dateContract;
    }

    @Column(name = "money_before_tax", nullable = false)
    @NotNull
    public Double getMoneyBeforeTax() {
        return moneyBeforeTax;
    }

    public void setMoneyBeforeTax(Double moneyBeforeTax) {
        this.moneyBeforeTax = moneyBeforeTax;
    }

    @Column(name = "money_of_tax", nullable = false)
    @NotNull
    public Double getMoneyOfTax() {
        return moneyOfTax;
    }

    public void setMoneyOfTax(Double moneyOfTax) {
        this.moneyOfTax = moneyOfTax;
    }

    @Column(name = "money_after_tax", nullable = false)
    @NotNull
    public Double getMoneyAfterTax() {
        return moneyAfterTax;
    }

    public void setMoneyAfterTax(Double moneyAfterTax) {
        this.moneyAfterTax = moneyAfterTax;
    }

    @Column(name = "currency", nullable = false)
    @NotNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public SalesContractStatus getStatus() {
        return status;
    }

    public void setStatus(SalesContractStatus status) {
        this.status = status;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "salesContract")
    public Set<DetailSalesContract> getDetailSalesContracts() {
        return detailSalesContracts;
    }

    public void setDetailSalesContracts(Set<DetailSalesContract> detailSalesContracts) {
        this.detailSalesContracts = detailSalesContracts;
    }

    public void addDetailSalesContract(DetailSalesContract detailSalesContract) {
        detailSalesContract.setSalesContract(this);
        detailSalesContracts.add(detailSalesContract);
    }
}

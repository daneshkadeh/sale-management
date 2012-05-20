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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "s_sales_contract")
public class SalesContract extends AbstractCodeOLObject {
    private SalesConfirm salesConfirm;
    private Partner supplier;
    private Date dateContract;
    private Money moneyBeforeTax;
    private Money moneyOfTax;
    private Money moneyAfterTax;
    private SalesContractStatus status = SalesContractStatus.OPEN;
    private Set<DetailSalesContract> detailSalesContracts = new HashSet<>();
    private Set<ImportationSC> listImportation = new HashSet<>();
    private Set<ContractDocument> listDocuments = new HashSet<>();
    private Set<PaymentSC> listPaymentSCs = new HashSet<>();
    // Should be change design multi-articles (same as ProductProperty) for other software customers.
    // Article 1
    private String remarkQuantity;
    private String remarkPacking;

    // Article 2
    private String remarkShipment;
    private Boolean isPartialShipment = false;
    private String discharginPort;
    private String loadingPort;

    // article 3
    private String remarkPayment;
    private String otherRequirement;

    // article 4
    private String importLicence;
    // article 5
    private String forceMajeure;

    // article 6
    private String arbitrarion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_confirm_id", nullable = false)
    @NotNull
    public SalesConfirm getSalesConfirm() {
        return salesConfirm;
    }

    public void setSalesConfirm(SalesConfirm salesConfirm) {
        this.salesConfirm = salesConfirm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    @NotNull
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

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_before_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_before_tax")) })
    @NotNull
    public Money getMoneyBeforeTax() {
        return moneyBeforeTax;
    }

    public void setMoneyBeforeTax(Money moneyBeforeTax) {
        this.moneyBeforeTax = moneyBeforeTax;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_of_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_tax")) })
    @NotNull
    public Money getMoneyOfTax() {
        return moneyOfTax;
    }

    public void setMoneyOfTax(Money moneyOfTax) {
        this.moneyOfTax = moneyOfTax;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money_after_tax")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_after_tax")) })
    @NotNull
    public Money getMoneyAfterTax() {
        return moneyAfterTax;
    }

    public void setMoneyAfterTax(Money moneyAfterTax) {
        this.moneyAfterTax = moneyAfterTax;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "salesContract")
    public Set<ImportationSC> getListImportation() {
        return listImportation;
    }

    public void setListImportation(Set<ImportationSC> listImportation) {
        this.listImportation = listImportation;
    }

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_contract_document", joinColumns = { @JoinColumn(name = "sales_contract_id") }, inverseJoinColumns = { @JoinColumn(name = "document_id") })
    public
            Set<ContractDocument> getListDocuments() {
        return listDocuments;
    }

    public void setListDocuments(Set<ContractDocument> listDocuments) {
        this.listDocuments = listDocuments;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "salesContract")
    public Set<PaymentSC> getListPaymentSCs() {
        return listPaymentSCs;
    }

    public void setListPaymentSCs(Set<PaymentSC> listPaymentSCs) {
        this.listPaymentSCs = listPaymentSCs;
    }

    /**
     * This method is only used to a non persisted paymentSC
     */
    public void addPaymentSC(PaymentSC payment) {
        payment.setSalesContract(this);
        listPaymentSCs.add(payment);
    }

    @Column(name = "remarkQuantity", length = 1024)
    public String getRemarkQuantity() {
        return remarkQuantity;
    }

    public void setRemarkQuantity(String remarkQuantity) {
        this.remarkQuantity = remarkQuantity;
    }

    @Column(name = "remarkPacking", length = 1024)
    public String getRemarkPacking() {
        return remarkPacking;
    }

    public void setRemarkPacking(String remarkPacking) {
        this.remarkPacking = remarkPacking;
    }

    @Column(name = "remarkShipment", length = 1024)
    public String getRemarkShipment() {
        return remarkShipment;
    }

    public void setRemarkShipment(String remarkShipment) {
        this.remarkShipment = remarkShipment;
    }

    @Column(name = "isPartialShipment")
    public Boolean getIsPartialShipment() {
        return isPartialShipment;
    }

    public void setIsPartialShipment(Boolean isPartialShipment) {
        this.isPartialShipment = isPartialShipment;
    }

    @Column(name = "discharginPort", length = 1024)
    public String getDischarginPort() {
        return discharginPort;
    }

    public void setDischarginPort(String discharginPort) {
        this.discharginPort = discharginPort;
    }

    @Column(name = "loadingPort", length = 1024)
    public String getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    @Column(name = "remarkPayment", length = 1024)
    public String getRemarkPayment() {
        return remarkPayment;
    }

    public void setRemarkPayment(String remarkPayment) {
        this.remarkPayment = remarkPayment;
    }

    @Column(name = "otherRequirement", length = 1024)
    public String getOtherRequirement() {
        return otherRequirement;
    }

    public void setOtherRequirement(String otherRequirement) {
        this.otherRequirement = otherRequirement;
    }

    @Column(name = "importLicence", length = 1024)
    public String getImportLicence() {
        return importLicence;
    }

    public void setImportLicence(String importLicence) {
        this.importLicence = importLicence;
    }

    @Column(name = "forceMajeure", length = 1024)
    public String getForceMajeure() {
        return forceMajeure;
    }

    public void setForceMajeure(String forceMajeure) {
        this.forceMajeure = forceMajeure;
    }

    @Column(name = "arbitrarion", length = 1024)
    public String getArbitrarion() {
        return arbitrarion;
    }

    public void setArbitrarion(String arbitrarion) {
        this.arbitrarion = arbitrarion;
    }
}

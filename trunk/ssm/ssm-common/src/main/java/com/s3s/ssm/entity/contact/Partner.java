/*
 * Partner
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

package com.s3s.ssm.entity.contact;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.config.UnitOfMeasure;

/**
 * A partner represents all the entities that you can do business with
 * 
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "s_partner")
@Inheritance(strategy = InheritanceType.JOINED)
public class Partner extends AbstractCodeOLObject {
    private static final long serialVersionUID = 1435468012252943876L;
    private String name;
    private Integer title; // Partner Form
    private String comment;// Notes
    private String website;
    private Boolean isCustomer;
    private Boolean isSupplier;
    private Boolean isEmployee;
    private Double debitLimit;// Payable Limit
    private UnitOfMeasure debitTimeUnit;// the unit of debit limit. Ex: date, month, year
    private Boolean isActive = true;

    private Set<PartnerCategory> partnerCateSet = new HashSet<PartnerCategory>();

    private Set<ContactDebt> contactDebtSet = new HashSet<ContactDebt>();

    @Column(name = "name", length = 128, nullable = false)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "title")
    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    @Column(name = "comment", length = 256)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "website", length = 256)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "is_customer")
    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    @Column(name = "is_supplier")
    public Boolean getIsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(Boolean isSupplier) {
        this.isSupplier = isSupplier;
    }

    @Column(name = "is_employee")
    public Boolean getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    @Column(name = "debit_limit")
    public Double getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(Double debitLimit) {
        this.debitLimit = debitLimit;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id")
    public UnitOfMeasure getDebitTimeUnit() {
        return debitTimeUnit;
    }

    public void setDebitTimeUnit(UnitOfMeasure debitTimeUnit) {
        this.debitTimeUnit = debitTimeUnit;
    }

    @Column(name = "is_active", nullable = false)
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    //
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "s_partner_partner_category", joinColumns = { @JoinColumn(name = "partner_id") }, inverseJoinColumns = { @JoinColumn(name = "partner_category_id") })
    public
            Set<PartnerCategory> getPartnerCateSet() {
        return partnerCateSet;
    }

    public void setPartnerCateSet(Set<PartnerCategory> partnerCateSet) {
        this.partnerCateSet = partnerCateSet;
    }

    @OneToMany(mappedBy = "partner")
    public Set<ContactDebt> getContactDebtSet() {
        return contactDebtSet;
    }

    public void setContactDebtSet(Set<ContactDebt> contactDebtSet) {
        this.contactDebtSet = contactDebtSet;
    }

}

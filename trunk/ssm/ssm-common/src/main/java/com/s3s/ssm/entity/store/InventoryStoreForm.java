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
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "store_inventory_form")
public class InventoryStoreForm extends AbstractCodeOLObject {
    private static final long serialVersionUID = 1L;
    private Date createdDate = new Date();
    private Store store;
    private Boolean isPrinted = false;
    private Boolean isSettingRealQtyTotal;
    private Operator staff;
    private String notes;
    private Integer curQtyTotal = 0;
    private Integer realQtyTotal = 0;
    private Money curAmtTotal;
    private Money realAmtTotal;
    private Set<DetailInventoryStore> detailInventoryStores = new HashSet<DetailInventoryStore>();

    @Column(name = "created_date")
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    @Column(name = "is_printed")
    public Boolean getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(Boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    @Column(name = "is_setting_real_qty_total")
    public Boolean getIsSettingRealQtyTotal() {
        return isSettingRealQtyTotal;
    }

    public void setIsSettingRealQtyTotal(Boolean isSettingRealQtyTotal) {
        this.isSettingRealQtyTotal = isSettingRealQtyTotal;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    public Operator getStaff() {
        return staff;
    }

    public void setStaff(Operator staff) {
        this.staff = staff;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "cur_qty_total")
    public Integer getCurQtyTotal() {
        return curQtyTotal;
    }

    public void setCurQtyTotal(Integer curQtyTotal) {
        this.curQtyTotal = curQtyTotal;
    }

    @Column(name = "real_qty_total")
    public Integer getRealQtyTotal() {
        return realQtyTotal;
    }

    public void setRealQtyTotal(Integer realQtyTotal) {
        this.realQtyTotal = realQtyTotal;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "cur_amt_total")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "curr_amt_code")) })
    public Money getCurAmtTotal() {
        return curAmtTotal;
    }

    public void setCurAmtTotal(Money curAmtTotal) {
        this.curAmtTotal = curAmtTotal;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "real_amt_total")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "real_amt_code")) })
    public Money getRealAmtTotal() {
        return realAmtTotal;
    }

    public void setRealAmtTotal(Money realAmtTotal) {
        this.realAmtTotal = realAmtTotal;
    }

    @OneToMany(mappedBy = "inventoryForm", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
    public Set<DetailInventoryStore> getDetailInventoryStores() {
        return detailInventoryStores;
    }

    public void setDetailInventoryStores(Set<DetailInventoryStore> detailInventoryStores) {
        this.detailInventoryStores = detailInventoryStores;
    }
}

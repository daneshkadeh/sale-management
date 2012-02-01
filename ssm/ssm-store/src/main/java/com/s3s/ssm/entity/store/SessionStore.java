/*
 * SessionStore
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

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Store;

@Entity
@Table(name = "s_session_store")
public class SessionStore extends AbstractIdOLObject {
    private Store store;
    private Date startDate;
    private Date endDate;
    private Integer referMonth;
    private Integer referQuarter;
    private Integer referYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    @NotNull
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Column(name = "start_date", nullable = false)
    @NotNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "refer_month", nullable = false)
    @NotNull
    public Integer getReferMonth() {
        return referMonth;
    }

    public void setReferMonth(Integer referMonth) {
        this.referMonth = referMonth;
    }

    @Column(name = "refer_quarter", nullable = false)
    @NotNull
    public Integer getReferQuarter() {
        return referQuarter;
    }

    public void setReferQuarter(Integer referQuarter) {
        this.referQuarter = referQuarter;
    }

    @Column(name = "refer_year", nullable = false)
    @NotNull
    public Integer getReferYear() {
        return referYear;
    }

    public void setReferYear(Integer referYear) {
        this.referYear = referYear;
    }

}

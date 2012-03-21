/*
 * CheckStore
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "s_check_store")
public class CheckStore extends AbstractIdOLObject {
    private com.s3s.ssm.entity.catalog.Store store; // TODO Hoang duplicate entity Store in catalog and store. Store in
                                                    // store has no @Table mark
    private Date dateStartCheck;
    private Date dateEndCheck;
    private String responsibleUser;
    private CheckStoreStatus status = CheckStoreStatus.OPEN;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    @NotNull
    public com.s3s.ssm.entity.catalog.Store getStore() {
        return store;
    }

    public void setStore(com.s3s.ssm.entity.catalog.Store store) {
        this.store = store;
    }

    @Column(name = "date_start_check", nullable = false)
    @NotNull
    public Date getDateStartCheck() {
        return dateStartCheck;
    }

    public void setDateStartCheck(Date dateStartCheck) {
        this.dateStartCheck = dateStartCheck;
    }

    @Column(name = "date_end_check")
    public Date getDateEndCheck() {
        return dateEndCheck;
    }

    public void setDateEndCheck(Date dateEndCheck) {
        this.dateEndCheck = dateEndCheck;
    }

    @Column(name = "responsible_user", nullable = false)
    @NotNull
    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public CheckStoreStatus getStatus() {
        return status;
    }

    public void setStatus(CheckStoreStatus status) {
        this.status = status;
    }

}

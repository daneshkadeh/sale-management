/*
 * ExchangeStoreForm
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

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.catalog.Store;

@Entity
@Table(name = "s_exchange_store_form")
public class ExchangeStoreForm extends AbstractCodeOLObject {
    private Store fromStore;
    private Store toStore;
    private String fromUser;
    private String toUser;
    private String responsibleUser;
    private Date createdDate;
    private Date sentDate;
    private Date receivedDate;
    private ExchangeStoreStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_store_id", nullable = false)
    @NotNull
    public Store getFromStore() {
        return fromStore;
    }

    public void setFromStore(Store fromStore) {
        this.fromStore = fromStore;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_store_id", nullable = false)
    @NotNull
    public Store getToStore() {
        return toStore;
    }

    public void setToStore(Store toStore) {
        this.toStore = toStore;
    }

    @Column(name = "from_user", nullable = false, length = 32)
    @NotNull
    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    @Column(name = "to_user", nullable = false, length = 32)
    @NotNull
    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Column(name = "responsible_user", nullable = false, length = 32)
    @NotNull
    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    @Column(name = "created_date", nullable = false)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "sent_date")
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Column(name = "received_date")
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public ExchangeStoreStatus getStatus() {
        return status;
    }

    public void setStatus(ExchangeStoreStatus status) {
        this.status = status;
    }

}

/*
 * DetailSesssionStore
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;

@Entity
@Table(name = "s_detail_session_store")
public class DetailSesssionStore extends AbstractIdOLObject {
    private SessionStore sessionStore;
    private Item item;
    private Integer beginAmount;
    private Integer endAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sess_store_id", nullable = false)
    public SessionStore getSessionStore() {
        return sessionStore;
    }

    public void setSessionStore(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "begin_amount", nullable = false)
    @NotNull
    public Integer getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(Integer beginAmount) {
        this.beginAmount = beginAmount;
    }

    @Column(name = "end_amount")
    public Integer getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(Integer endAmount) {
        this.endAmount = endAmount;
    }

}

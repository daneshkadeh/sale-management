/*
 * MoveOrder
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.AbstractCodeOLObject;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "store_move_order")
public class MoveStoreOrder extends AbstractCodeOLObject {
    private Date createdDate = new Date();
    private Date fromDate = new Date();
    private Date destDate = new Date();
    private Store fromStore;
    private Store destStore;
    private MoveStoreStatus status = MoveStoreStatus.NEW;
    private Set<MoveStoreForm> moveStoreForms = new HashSet<MoveStoreForm>();

    @Column(name = "created_date", nullable = false)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "from_date")
    @NotNull
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Column(name = "dest_date")
    @NotNull
    public Date getDestDate() {
        return destDate;
    }

    public void setDestDate(Date destDate) {
        this.destDate = destDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_store_id")
    @NotNull
    public Store getFromStore() {
        return fromStore;
    }

    public void setFromStore(Store fromStore) {
        this.fromStore = fromStore;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dest_store_id")
    @NotNull
    public Store getDestStore() {
        return destStore;
    }

    public void setDestStore(Store destStore) {
        this.destStore = destStore;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public MoveStoreStatus getStatus() {
        return status;
    }

    public void setStatus(MoveStoreStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "moveStoreOrder", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL })
    public Set<MoveStoreForm> getMoveStoreForms() {
        return moveStoreForms;
    }

    public void setMoveStoreForms(Set<MoveStoreForm> moveStoreForms) {
        this.moveStoreForms = moveStoreForms;
    }
}

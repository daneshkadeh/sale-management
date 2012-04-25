/*
 * ClosingEntry
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * @author Le Thanh Hoang
 * 
 *         One of the final entries made at year-end to close accounts and transfer the amounts to financial statements.
 */
@Entity
@Table(name = "store_store_closing_entry")
public class ClosingStoreEntry extends AbstractIdOLObject {
    private static final long serialVersionUID = 3307270397994078601L;
    private Store store;
    private Date closingDate = new Date();
    private Set<DetailClosingStore> closingStoreSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    @NotNull
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Column(name = "closing_date")
    @NotNull
    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    @OneToMany(mappedBy = "closingEntry", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
    public Set<DetailClosingStore> getClosingStoreSet() {
        return closingStoreSet;
    }

    public void setClosingStoreSet(Set<DetailClosingStore> closingStoreSet) {
        this.closingStoreSet = closingStoreSet;
    }

}

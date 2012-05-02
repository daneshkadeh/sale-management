/*
 * ClosingFinanceEntry
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

package com.s3s.ssm.entity.finance;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "finance_finance_closing_entry")
public class ClosingFinanceEntry extends AbstractIdOLObject {
    private static final long serialVersionUID = -8568641623738107930L;
    private Date closingDate = new Date();
    private Set<DetailClosingFinance> closingFinanceSet = new HashSet<DetailClosingFinance>();

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
    public Set<DetailClosingFinance> getClosingFinanceSet() {
        return closingFinanceSet;
    }

    public void setClosingFinanceSet(Set<DetailClosingFinance> closingFinanceSet) {
        this.closingFinanceSet = closingFinanceSet;
    }

}

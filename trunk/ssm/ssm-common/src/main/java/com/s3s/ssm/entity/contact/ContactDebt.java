/*
 * ContactDebt
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "co_contact_debt")
public class ContactDebt extends AbstractIdOLObject {
    /**
     * 
     */
    private static final long serialVersionUID = 4941626566283215149L;
    private Partner partner;
    private Double debtMoney;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Column(name = "debt_money", nullable = false)
    @NotNull
    public Double getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(Double debtMoney) {
        this.debtMoney = debtMoney;
    }

    // TODO: draft code
    @Column(name = "currency_id", nullable = false)
    @NotNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}

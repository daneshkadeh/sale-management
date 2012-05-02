/*
 * ClosingStore
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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "store_detail_finance_closing")
public class DetailClosingFinance extends AbstractIdOLObject {
    private static final long serialVersionUID = -2900305096490940888L;
    private ClosingFinanceEntry closingEntry;
    private Partner partner;
    private Money debt = Money.create(CurrencyEnum.VND, 0L);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "closing_entry_id")
    @NotNull
    public ClosingFinanceEntry getClosingEntry() {
        return closingEntry;
    }

    public void setClosingEntry(ClosingFinanceEntry closingEntry) {
        this.closingEntry = closingEntry;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id")
    @NotNull
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "debt")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code1")) })
    public Money getDebt() {
        return debt;
    }

    public void setDebt(Money debt) {
        this.debt = debt;
    }

}

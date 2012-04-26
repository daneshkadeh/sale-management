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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "co_contact_debt")
public class ContactDebt extends AbstractIdOLObject {
    /**
     * 
     */
    private static final long serialVersionUID = 4941626566283215149L;
    private Partner partner;
    // If customer loans 100 VND, the value must be 100 VND.
    // If THU loans supplier 1000 VND, the value must be 1000 VND
    private Money debtMoney = Money.create(CurrencyEnum.VND, 0L);

    @OneToOne
    @JoinColumn(name = "partner_id", nullable = false)
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "debtAmount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    @NotNull
    public Money getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(Money debtMoney) {
        this.debtMoney = debtMoney;
    }

}

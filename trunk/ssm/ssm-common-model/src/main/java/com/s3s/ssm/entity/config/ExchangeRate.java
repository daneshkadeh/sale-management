/*
 * ExchangeRate
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
package com.s3s.ssm.entity.config;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "config_exchange_rate")
public class ExchangeRate extends AbstractIdOLObject {
    private static final long serialVersionUID = -9188655499937108343L;
    private Date updateDate = new Date(); // default is current date
    private SCurrency currency;
    private Double rate;

    @Column(name = "update_date")
    @NotNull
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id")
    @NotNull
    public SCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(SCurrency currency) {
        this.currency = currency;
    }

    @Column(name = "rate")
    @NotNull
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}

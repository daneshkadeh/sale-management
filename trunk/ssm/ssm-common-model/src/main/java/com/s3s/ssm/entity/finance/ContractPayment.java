/*
 * ContractPayment
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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.sales.SalesContract;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "finance_contract_payment")
@PrimaryKeyJoinColumn(name = "contract_payment_id")
public class ContractPayment extends Payment {
    private SalesContract salesContract;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "sales_contract_id")
    public SalesContract getSalesContract() {
        return salesContract;
    }

    public void setSalesContract(SalesContract salesContract) {
        this.salesContract = salesContract;
    }

}

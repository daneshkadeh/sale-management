/*
 * DebtHistoryDTO
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

package com.s3s.ssm.dto.contact;

import java.io.Serializable;

import com.s3s.ssm.model.Money;

/**
 * @author Le Thanh Hoang
 * 
 */
public class LongDebtCustDTO implements Serializable {
    private static final long serialVersionUID = -5347517188449726815L;
    private String custCode;
    private String custName;
    private Money quotaAmt;
    private Money debtAmt;
    private Money remainingAmt;

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Money getQuotaAmt() {
        return quotaAmt;
    }

    public void setQuotaAmt(Money quotaAmt) {
        this.quotaAmt = quotaAmt;
    }

    public Money getDebtAmt() {
        return debtAmt;
    }

    public void setDebtAmt(Money debtAmt) {
        this.debtAmt = debtAmt;
    }

    public Money getRemainingAmt() {
        return remainingAmt;
    }

    public void setRemainingAmt(Money remainingAmt) {
        this.remainingAmt = remainingAmt;
    }

}

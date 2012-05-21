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

package com.s3s.ssm.dto.finance;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.model.Money;

/**
 * @author Le Thanh Hoang
 * 
 */
public class CustDebtDTO extends AbstractBaseIdObject {
    private static final long serialVersionUID = 4637727389316618317L;
    private String code;
    private String name;
    private Money debtAmt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getDebtAmt() {
        return debtAmt;
    }

    public void setDebtAmt(Money debtAmt) {
        this.debtAmt = debtAmt;
    }

}

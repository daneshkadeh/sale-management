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

import java.util.Date;

import com.s3s.ssm.entity.AbstractBaseIdObject;

/**
 * @author Le Thanh Hoang
 * 
 */
public class CustDebtHistoryDTO extends AbstractBaseIdObject {
    private static final Long serialVersionUID = -5048309040005476810L;
    private Date hisDate;
    private String code;
    private Enum contentType;
    private String content;
    private Long amt = 0L;
    private Long payAmt = 0L;
    private Long advanceAmt = 0L;
    private Long debtAmt = 0L;

    public Date getHisDate() {
        return hisDate;
    }

    public void setHisDate(Date hisDate) {
        this.hisDate = hisDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Enum getContentType() {
        return contentType;
    }

    public void setContentType(Enum contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public Long getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(Long payAmt) {
        this.payAmt = payAmt;
    }

    public Long getAdvanceAmt() {
        return advanceAmt;
    }

    public void setAdvanceAmt(Long advanceAmt) {
        this.advanceAmt = advanceAmt;
    }

    public Long getDebtAmt() {
        return debtAmt;
    }

    public void setDebtAmt(Long debtAmt) {
        this.debtAmt = debtAmt;
    }

}

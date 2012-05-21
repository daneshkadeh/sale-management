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

package com.s3s.ssm.dto.catalog;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Le Thanh Hoang
 * 
 */
public class LongUnsoldProductDTO implements Serializable {
    private static final long serialVersionUID = -5126009500663282937L;
    private String goodsCode;
    private String goodsName;
    private Date latestSellDate;
    private Integer unsoldDayNum;
    private Long mustSoldPeriod;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Date getLatestSellDate() {
        return latestSellDate;
    }

    public void setLatestSellDate(Date latestSellDate) {
        this.latestSellDate = latestSellDate;
    }

    public Integer getUnsoldDayNum() {
        return unsoldDayNum;
    }

    public void setUnsoldDayNum(Integer unsoldDayNum) {
        this.unsoldDayNum = unsoldDayNum;
    }

    public Long getMustSoldPeriod() {
        return mustSoldPeriod;
    }

    public void setMustSoldPeriod(Long mustSoldPeriod) {
        this.mustSoldPeriod = mustSoldPeriod;
    }

}

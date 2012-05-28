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

package com.s3s.ssm.dto.sales;

import java.io.Serializable;

/**
 * @author Le Thanh Hoang
 * @see
 */
public class TopProductInMonthDTO implements Serializable {
    private static final long serialVersionUID = 6356408916936099600L;
    private String goodsCode;
    private String goodsName;
    private Integer quotaQty = 0;
    private Integer soldQty = 0;
    private Float rate = 0F;

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

    public Integer getQuotaQty() {
        return quotaQty;
    }

    public void setQuotaQty(Integer quotaQty) {
        this.quotaQty = quotaQty;
    }

    public Integer getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(Integer soldQty) {
        this.soldQty = soldQty;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

}

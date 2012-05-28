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

package com.s3s.ssm.dto.store;

import java.io.Serializable;

/**
 * @author Le Thanh Hoang
 * 
 */
public class LowProductInStoreDTO implements Serializable {
    private static final long serialVersionUID = 4264983532241361085L;
    private String goodsCode;
    private String goodsName;
    private String storeName;
    private Integer quotaQty = 0;
    private Integer actualQty = 0;
    private Integer varianceQty = 0;

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getQuotaQty() {
        return quotaQty;
    }

    public void setQuotaQty(Integer quotaQty) {
        this.quotaQty = quotaQty;
    }

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    public Integer getVarianceQty() {
        return varianceQty;
    }

    public void setVarianceQty(Integer varianceQty) {
        this.varianceQty = varianceQty;
    }

}

/*
 * StoreUtil
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

package com.s3s.ssm.view.util;

import org.apache.commons.lang.math.NumberUtils;

import com.s3s.ssm.model.Money;

/**
 * @author Le Thanh Hoang
 * 
 */
public class StoreHelper {
    public static Money calculatePriceSubtotal(String strQuantity, Money mPriceUnit) {
        int quan = NumberUtils.toInt(strQuantity);
        Money mPriceSubtotal = mPriceUnit.multiply(quan);
        return mPriceSubtotal;
    }
}

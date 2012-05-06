/*
 * InvoiceType
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
package com.s3s.ssm.entity.sales;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * Type of invoice is used to identify actions on goods. Eg. Goods of SUPPORT, TRIAL must be returned to THU after 1
 * year. <li>SALES: payments are positive. <li>REFUND: payments are negative. <li>MAINTAINANCE, SUPPORT, TRIAL: there is
 * no payment.</li> <li>RENT: payments is positive, the money of invoice is not total money of goods. Goods are for
 * rent.</li>
 * 
 * @author phamcongbang
 * 
 */
public enum InvoiceType {
    SALES, REFUND, MAINTAINANCE, SUPPORT, TRIAL, RENT;
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
    }
}

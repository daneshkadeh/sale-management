/*
 * SalesContractStatus
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
 * OPEN: only create data, not send sales contract to customer. </br> SENT: sent to customer. </br> PROCESSING:
 * receiving products. </br> CLOSED: SalesContract is done. All products has been imported. </br> SalesContract is
 * cancelled, no product is imported.
 * 
 * @author phamcongbang
 * 
 */
public enum SalesContractStatus {
    OPEN, SENT, PROCESSING, CLOSED, CANCELLED;
    @Override
    public String toString() {
        return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
    }
}

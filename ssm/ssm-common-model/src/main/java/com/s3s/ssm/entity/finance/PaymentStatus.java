/*
 * PaymentStatus
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
package com.s3s.ssm.entity.finance;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

public enum PaymentStatus {
    OPEN, CLOSED, CANCELLED, DRAFT;
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
    }
}

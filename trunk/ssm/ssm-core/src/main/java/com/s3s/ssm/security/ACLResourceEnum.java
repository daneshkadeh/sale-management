/*
 * ACLResourceEnum
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
package com.s3s.ssm.security;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

public enum ACLResourceEnum {
    // config module
    CURRENCY(1, ControlConfigUtils.getString("label.resource.currency")), 
    EXCHANGE_RATE(2, ControlConfigUtils.getString("label.resource.exchange_rate")), 
    MANUFACTURER(3, ControlConfigUtils.getString("label.resource.manufacturer")), 
    BASIC_INFORMATION(4, ControlConfigUtils.getString("label.resource.basic_information")), 
    UOM(5, ControlConfigUtils.getString("label.resource.uom")), 
    UOM_CATEGORY(6, ControlConfigUtils.getString("label.resource.uom_category"));
    // the order on ACL panel in Access rule
    private int order;
    private String label;

    ACLResourceEnum(int order, String label) {
        this.order = order;
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public String getLabel() {
        return label;
    }

}
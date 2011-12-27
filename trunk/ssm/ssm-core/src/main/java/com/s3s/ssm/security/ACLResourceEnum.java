package com.s3s.ssm.security;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

public enum ACLResourceEnum {
    UOM_CATEGORY(0, ControlConfigUtils.getString("label.resource.uom_category")),
    PRODUCT(1, ControlConfigUtils.getString("label.resource.product")),
    ROLE(2, ControlConfigUtils.getString("label.resource.role"));
    //the order on ACL panel in Access rule
    private int order;
    private String message;
    ACLResourceEnum(int order, String message) {
        this.order = order;
        this.message = message;
    }
    public int getOrder() {
        return order;
    }
    public String getMessage() {
        return message;
    }
    
}
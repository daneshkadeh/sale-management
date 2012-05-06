package com.s3s.ssm.entity.contact;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * ROLE of individual in application, can be applied for all companies.
 * 
 * @author phamcongbang
 * 
 */
public enum IndividualRoleEnum {
    MAIN, REPRENTER, MEMBER;
    @Override
    public String toString() {
        return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
    }
}

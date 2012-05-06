package com.s3s.ssm.entity.contact;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * Title of an individual: Mr, Mrs, Miss, Mr and Mrs
 * 
 * @author phamcongbang
 * 
 */
public enum IndividualTitleEnum {
    MR, MRS, MISS, MR_MRS, UNDEFINED;
    @Override
    public String toString() {
        return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
    }
}

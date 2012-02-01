/*
 * AbstractCodeOLObject
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
package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Entity with code 32 characters
 * 
 * @author phamcongbang
 * 
 */
@MappedSuperclass
public abstract class AbstractCodeOLObject extends AbstractIdOLObject {
    private String code;

    @Column(name = "code", nullable = false, length = 32)
    @NotNull
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Transient
    @Override
    public String toString() {
        return getCode();
    }
}

/*
 * CustomerTest
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
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_customer")
public class CustomerTest extends AbstractBaseIdObject {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false)
    @NotNull
    public String getName() {
        return name;
    }
}

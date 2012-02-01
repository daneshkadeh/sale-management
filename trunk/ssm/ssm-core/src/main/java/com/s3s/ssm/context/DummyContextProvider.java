/*
 * DummyContextProvider
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
package com.s3s.ssm.context;

import java.util.Set;

import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;

//@Service("contextProvider")
public class DummyContextProvider implements ContextProvider {

    @Override
    public String getCurrentUser() {
        return "admin";
    }

    @Override
    public Long getCurrentPOS() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Float getCurrencyRate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPaymentMethod() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<CustomPermission> getPermissions(ACLResourceEnum aclResource) {
        // TODO Auto-generated method stub
        return null;
    }

}

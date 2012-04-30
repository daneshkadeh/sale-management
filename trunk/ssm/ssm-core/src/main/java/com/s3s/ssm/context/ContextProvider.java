/*
 * ContextProvider
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

/**
 * This interface help to get current user and current point of sales, current Institution, current Organization,
 * security stuffs.
 * 
 * @author phamcongbang
 * 
 */
public interface ContextProvider {
    public void loadContext();

    public void clearContext();

    public void saveContext();

    public String getCurrentUser();

    public Long getCurrentPOS();

    public Float getCurrencyRate();

    public String getPaymentMethod();

    Set<CustomPermission> getPermissions(ACLResourceEnum aclResource);
}

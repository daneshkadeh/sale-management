/*
 * OptimisticLockingInterceptor
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
package com.s3s.ssm.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.EmptyInterceptor;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.ConfigProvider;

public class OptimisticLockingInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
            String[] propertyNames, org.hibernate.type.Type[] types) {
        if (entity instanceof AbstractIdOLObject) {
            int indexOf = ArrayUtils.indexOf(propertyNames, "dateLastUpdate");
            currentState[indexOf] = new Date();

            int indexOfULU = ArrayUtils.indexOf(propertyNames, "userLastUpdate");
            currentState[indexOfULU] = ConfigProvider.getInstance().getContextProvider().getCurrentUser();
            return true;
        }
        return false;
    };

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
            org.hibernate.type.Type[] types) {
        if (entity instanceof AbstractIdOLObject) {
            Date currentDate = new Date();
            int indexOfDI = ArrayUtils.indexOf(propertyNames, "dateInserted");
            state[indexOfDI] = currentDate;

            int indexOfDLU = ArrayUtils.indexOf(propertyNames, "dateLastUpdate");
            state[indexOfDLU] = currentDate;

            int indexOfUI = ArrayUtils.indexOf(propertyNames, "userInserted");
            state[indexOfUI] = ConfigProvider.getInstance().getContextProvider().getCurrentUser();

            int indexOfULU = ArrayUtils.indexOf(propertyNames, "userLastUpdate");
            state[indexOfULU] = ConfigProvider.getInstance().getContextProvider().getCurrentUser();

            return true;
        }
        return false;
    }

}

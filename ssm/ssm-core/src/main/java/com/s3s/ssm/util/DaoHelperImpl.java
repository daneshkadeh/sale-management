/*
 * DaoHelperImpl
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
package com.s3s.ssm.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Repository;

import com.s3s.ssm.dao.IBaseDao;

/**
 * This class help to get DAO from an entity class. Support working with entity.
 * 
 * @author phamcongbang
 * 
 */
@Repository("daoHelper")
public class DaoHelperImpl implements DaoHelper {
    private final Map<Class<?>, IBaseDao<?>> mapDAOs = new HashMap<Class<?>, IBaseDao<?>>();

    public DaoHelperImpl() {
        initDAOs();
    }

    /**
     * Loop all entity package com.s3s.ssm.entity to get classes. Get DAOs from spring bean or create DAO
     * HibernateBaseDaoImpl Then put into mapDAOs. TODO: This is not working.
     */
    private void initDAOs() {
        // Todo: loop all entity package com.s3s.ssm.entity to get classes
        // get DAOs from spring bean or create DAO HibernateBaseDaoImpl
        // Maybe we have to set up DAO context in this method.
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> IBaseDao<T> getDao(Class<T> clazz) {
        IBaseDao<T> dao = (IBaseDao<T>) mapDAOs.get(clazz);
        if (dao == null) {
            try {
                dao = (IBaseDao<T>) ConfigProvider.getInstance().getApplicationContext()
                        .getBean(StringUtils.uncapitalize(clazz.getSimpleName()) + "Dao");
            } catch (BeansException e) {
                dao = (IBaseDao<T>) ConfigProvider.getInstance().getApplicationContext().getBean("defaultBaseDao");
            }
            dao.setEntityClass(clazz);
            mapDAOs.put(clazz, dao);
        }
        return dao;
    }
}

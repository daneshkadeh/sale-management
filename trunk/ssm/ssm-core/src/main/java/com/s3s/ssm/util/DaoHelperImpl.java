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
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.interceptor.OptimisticLockingInterceptor;

/**
 * This class help to get DAO from an entity class. Support working with entity.
 * 
 * @author phamcongbang
 * 
 */
@Repository("daoHelper")
public class DaoHelperImpl extends HibernateDaoSupport implements DaoHelper {
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

    /**
     * Down cast a object in the specified class given in parameter. If the object is a hibernate proxy, we load it
     * before the down casting to remove {@link ClassCastException}.
     */
    @SuppressWarnings("unchecked")
    public static <U extends Object, T extends U> T downCast(final Class<T> entityClass, final U objectToCast) {
        Assert.notNull(entityClass, "the 'entityClass' parameter cannot be null!");
        Assert.notNull(objectToCast, "the 'objectToCast' parameter cannot be null!");

        if (objectToCast instanceof HibernateProxy) {
            return (T) ((HibernateProxy) objectToCast).getHibernateLazyInitializer().getImplementation();
        } else {
            return (T) objectToCast;
        }
    }

    @Autowired
    public void anyMethodName(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        OptimisticLockingInterceptor interceptor = new OptimisticLockingInterceptor();

        sessionFactory.openSession(interceptor);
        return super.createHibernateTemplate(sessionFactory);
    }
}

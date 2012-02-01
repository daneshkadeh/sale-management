/*
 * BaseDaoImpl
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
package com.s3s.ssm.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.s3s.ssm.context.ContextProvider;
import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.interceptor.OptimisticLockingInterceptor;

public class BaseDaoImpl<T extends AbstractBaseIdObject> extends HibernateDaoSupport implements IBaseDao<T> {

    @Autowired
    ContextProvider contextProvider;

    private Class<T> clazz;

    public BaseDaoImpl() {
        clazz = getEntityClass();
    }

    @Override
    public void setEntityClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        if (clazz == null) {
            Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            clazz = (Class<T>) controllerType;
        }
        return clazz;
    }

    public BaseDaoImpl(Class<T> objectClazz) {
        this.clazz = objectClazz;
    }

    @Autowired
    public void anyMethodName(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        OptimisticLockingInterceptor interceptor = new OptimisticLockingInterceptor();

        sessionFactory.openSession(interceptor);
        return super.createHibernateTemplate(sessionFactory);
    }

    @Override
    public T save(T entity) {
        getHibernateTemplate().save(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        getHibernateTemplate().update(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void deleteAll(Collection<T> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            getHibernateTemplate().deleteAll(entities);
        }
    }

    @Override
    public Collection<T> saveOrUpdateAll(Collection<T> list) {
        getHibernateTemplate().saveOrUpdateAll(list);
        return list;
    }

    @Override
    public T saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(Long id) {
        return (T) getHibernateTemplate().get(getEntityClass(), id);
    }

    @Override
    public List<T> findAll() {
        List<T> list = getHibernateTemplate().find("from " + getEntityClass().getSimpleName());
        return list;
    }

    @Override
    public DetachedCriteria getCriteria() {
        return DetachedCriteria.forClass(getEntityClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Counts the number of results of a search.
     * 
     * @param criteria
     *            The criteria for the query.
     * @return The number of results of the query.
     * @throws DataAccessException
     */
    @Override
    public int findCountByCriteria(final DetachedCriteria criteria) throws DataAccessException {

        Assert.notNull(criteria, "DetachedCriteria must not be null");
        Object result = getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria executableCriteria = criteria.getExecutableCriteria(session);
                executableCriteria.setProjection(Projections.rowCount());
                return executableCriteria.uniqueResult();
            }
        });
        if (result == null) {
            result = 0;
        }
        return (Integer) result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

}
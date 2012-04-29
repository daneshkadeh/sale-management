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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.s3s.ssm.context.ContextProvider;
import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.IActiveObject;
import com.s3s.ssm.interceptor.OptimisticLockingInterceptor;

public class BaseDaoImpl<T extends AbstractBaseIdObject> extends HibernateDaoSupport implements IBaseDao<T> {

    @Autowired
    ContextProvider contextProvider;

    private Class<T> clazz;

    public BaseDaoImpl() {
        clazz = getEntityClass();
    }

    /**
     * 
     * {@inheritDoc}
     */
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

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public T save(T entity) {
        getHibernateTemplate().save(entity);
        return entity;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public T update(T entity) {
        getHibernateTemplate().update(entity);
        return entity;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void deleteAll(Collection<T> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            getHibernateTemplate().deleteAll(entities);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Collection<T> saveOrUpdateAll(Collection<T> list) {
        getHibernateTemplate().saveOrUpdateAll(list);
        return list;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public T saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
        return entity;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findById(Long id) {
        return (T) getHibernateTemplate().get(getEntityClass(), id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findByCode(String code) {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("code", code));
        List<T> list = getHibernateTemplate().findByCriteria(criteria);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<T> findAll() {
        return getHibernateTemplate().loadAll(getEntityClass());
    }

    @Override
    public List<T> findAllActive() {
        List<T> entities = findAll();
        List<T> removedList = new ArrayList<>();
        if (IActiveObject.class.isAssignableFrom(getEntityClass())) {
            for (T entity : entities) {
                if (!((IActiveObject) entity).isActive()) {
                    removedList.add(entity);
                }
            }
        }
        entities.removeAll(removedList);
        return entities;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public DetachedCriteria getCriteria() {
        return DetachedCriteria.forClass(getEntityClass());
    }

    /**
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public T findFirstByCriteria(DetachedCriteria criteria) {
        List<T> list = findByCriteria(criteria, 0, 1);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * This implements for MySQL DB. Use the supported function if using other DB. </br> {@inheritDoc}
     */
    @Override
    public long getNextSequence(final String name) {
        Object result = getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createSQLQuery("select seq('" + name + "')");
                long uniqueResult = ((BigInteger) query.uniqueResult()).longValue();
                if (uniqueResult == 0) {
                    query = session.createSQLQuery("insert into `seq` values('" + name + "', 1)");
                    query.executeUpdate();
                    uniqueResult = 1;
                }
                return uniqueResult;
            }
        });
        return (long) result;
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

    @Override
    public HibernateTemplate getHibernateTmpl() {
        return getHibernateTemplate();
    }
}
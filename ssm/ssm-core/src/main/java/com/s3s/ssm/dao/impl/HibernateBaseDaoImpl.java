package com.s3s.ssm.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.s3s.ssm.context.ContextProvider;
import com.s3s.ssm.dao.HibernateBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;

public class HibernateBaseDaoImpl<T extends AbstractBaseIdObject> extends HibernateDaoSupport implements
        HibernateBaseDao<T> {
    @Autowired
    ContextProvider contextProvider;

    private Class<T> clazz;

    public HibernateBaseDaoImpl() {
        clazz = getEntityClass();
    }

    @Override
    public void setEntityClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected Class<T> getEntityClass() {
        if (clazz == null) {
            Type controllerType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            clazz = (Class<T>) controllerType;
        }
        return clazz;
    }

    public HibernateBaseDaoImpl(Class<T> objectClazz) {
        this.clazz = objectClazz;
    }

    @Autowired
    public void anyMethodName(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public void save(T entity) {
        addCommonFields(entity);
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(T entity) {
        addCommonFields(entity);
        getHibernateTemplate().update(entity);
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
    public void saveOrUpdateAll(Collection<T> list) {
        for (T entity : list) {
            addCommonFields(entity);
        }
        getHibernateTemplate().saveOrUpdateAll(list);
    }

    @Override
    public void saveOrUpdate(T entity) {
        addCommonFields(entity);
        getHibernateTemplate().saveOrUpdate(entity);
    }

    private void addCommonFields(T entity) {
        if (entity instanceof AbstractIdOLObject) {
            AbstractIdOLObject olObject = (AbstractIdOLObject) entity;
            Date currentDate = new Date();
            if (!olObject.isPersisted()) {
                olObject.setUserInserted(contextProvider.getCurrentUser());
                olObject.setDateInserted(currentDate);
            }
            olObject.setDateLastUpdate(currentDate);
            olObject.setUserLastUpdate(contextProvider.getCurrentUser());
        }
    }

    @Override
    public T findById(Long id) {
        List<T> list = getHibernateTemplate().find("from " + getEntityClass().getSimpleName() + " where id=?", id);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
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

    /**
     * 
     * {@inheritDoc}
     * <p>
     * A delegation of {@link HibernateTemplate#findByCriteria(DetachedCriteria, int, int)}.
     * 
     */
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

}
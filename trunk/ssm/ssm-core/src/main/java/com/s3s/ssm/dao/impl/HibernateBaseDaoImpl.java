package com.s3s.ssm.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.s3s.ssm.dao.HibernateBaseDao;
import com.s3s.ssm.entity.AbstractBaseIdObject;

public class HibernateBaseDaoImpl<T extends AbstractBaseIdObject> extends HibernateDaoSupport implements
        HibernateBaseDao<T> {
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

    public HibernateBaseDaoImpl(Class objectClazz) {
        this.clazz = objectClazz;
    }

    @Autowired
    public void anyMethodName(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public void save(AbstractBaseIdObject entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(AbstractBaseIdObject entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(AbstractBaseIdObject entity) {
        getHibernateTemplate().delete(entity);

    }

    @Override
    public void saveOrUpdateAll(List<T> list) {
        getHibernateTemplate().saveOrUpdateAll(list);
    }

    @Override
    public void saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public T findById(Integer id) {
        List list = getHibernateTemplate().find("from " + getEntityClass().getSimpleName() + " where id=?", id);
        if (CollectionUtils.isNotEmpty(list)) {
            return (T) list.get(0);
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

    @Override
    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

}
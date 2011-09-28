package com.hbsoft.ssm.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;

public class HibernateBaseDaoImpl<T extends AbstractBaseIdObject> extends HibernateDaoSupport {
    private Class clazz;

    public HibernateBaseDaoImpl() {
        clazz = getEntityClass();
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

    public void save(AbstractBaseIdObject entity) {
        getHibernateTemplate().save(entity);
    }

    public void update(AbstractBaseIdObject entity) {
        getHibernateTemplate().update(entity);
    }

    public void delete(AbstractBaseIdObject entity) {
        getHibernateTemplate().delete(entity);

    }

    public void saveOrUpdateAll(List<T> list) {
        getHibernateTemplate().saveOrUpdateAll(list);
    }

    public T findById(Integer id) {
        List list = getHibernateTemplate().find("from " + getEntityClass().getSimpleName() + " where id=?", id);
        if (CollectionUtils.isNotEmpty(list)) {
            return (T) list.get(0);
        }
        return null;
    }

    public List<T> findAll() {
        List<T> list = getHibernateTemplate().find("from " + getEntityClass().getSimpleName());
        return list;
    }

    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return (List<T>) getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }

}
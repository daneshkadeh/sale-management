package com.s3s.ssm.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.s3s.ssm.entity.AbstractBaseIdObject;

public interface HibernateBaseDao<T> {

    public void setEntityClass(Class<T> clazz);

    public void save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public void saveOrUpdate(T entity);

    public void saveOrUpdateAll(List<T> list);

    public T findById(Integer id);

    public List<T> findAll();

    public DetachedCriteria getCriteria(Class<? extends AbstractBaseIdObject> clazz);

    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

    public List<T> findByCriteria(DetachedCriteria dc);
}

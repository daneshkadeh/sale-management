package com.hbsoft.ssm.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface HibernateBaseDao<T> {

    public void setEntityClass(Class<T> clazz);

    public void save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public void saveOrUpdateAll(List<T> list);

    public T findById(Integer id);

    public List<T> findAll();

    public DetachedCriteria getCriteria();

    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);
}

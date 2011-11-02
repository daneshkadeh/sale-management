package com.s3s.ssm.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface IBaseDao<T> {

    public void setEntityClass(Class<T> clazz);

    public void save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public void saveOrUpdate(T entity);

    public void saveOrUpdateAll(Collection<T> list);

    public T findById(Long id);

    public List<T> findAll();

    public DetachedCriteria getCriteria();

    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

    public List<T> findByCriteria(DetachedCriteria dc);

    void deleteAll(Collection<T> entities);
}

package com.s3s.ssm.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface IBaseDao<T> {

    void setEntityClass(Class<T> clazz);

    T save(T entity);

    T update(T entity);

    void delete(T entity);

    T saveOrUpdate(T entity);

    Collection<T> saveOrUpdateAll(Collection<T> list);

    T findById(Long id);

    List<T> findAll();

    DetachedCriteria getCriteria();

    List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

    List<T> findByCriteria(DetachedCriteria dc);

    void deleteAll(Collection<T> entities);

    void flush();
}

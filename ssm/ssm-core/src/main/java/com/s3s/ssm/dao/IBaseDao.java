package com.s3s.ssm.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

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

    /**
     * A delegation of {@link HibernateTemplate#findByCriteria(DetachedCriteria, int, int)}.
     */
    List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

    /**
     * A delegation of {@link HibernateTemplate#findByCriteria(DetachedCriteria)}.
     */
    List<T> findByCriteria(DetachedCriteria dc);

    /**
     * Retrieves the number of domain objects matching the Hibernate criteria.
     * 
     * @param hibernateCriteria
     *            the criteria that the result has to fulfill <b>Note: Do not reuse criteria objects! They need to
     *            recreated (or cloned e.g. using <tt>SerializationUtils.clone()</tt>) per execution, due to the
     *            suboptimal design of Hibernate's criteria facility.</b>
     * @return the number of objects that fulfill the criteria
     * @throws DataAccessException
     * 
     * @see ConvenienceHibernateTemplate#findCountByCriteria(DetachedCriteria)
     */
    public int findCountByCriteria(DetachedCriteria hibernateCriteria) throws DataAccessException;

    void deleteAll(Collection<T> entities);

    void flush();
}

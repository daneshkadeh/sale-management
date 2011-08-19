package com.hbsoft.ssm.dao;

import java.util.Collection;
import java.util.List;

import com.hbsoft.ssm.entity.AbstractEntity;

/**
 * The interface includes common functions for DAO.
 * 
 * @author Phan Hong Phuc
 */
public interface IDao<T extends AbstractEntity> {
	void save(T t);

	void update(T t);

	void delete(T t);

	T findById(Integer id);

	List<T> findAll();

	List<T> findLikeName(String name);

	void saveOrUpdate(Collection<T> collection);
}

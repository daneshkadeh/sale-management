package com.hbsoft.ssm.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.util.CollectionHelper;
import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.dao.CustomerDao;
import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.util.CustomHibernateDaoSupport;

@Repository("customerDao")
public class CustomerDaoImpl extends CustomHibernateDaoSupport implements
		CustomerDao {

	public void save(Customer customer) {
		getHibernateTemplate().save(customer);
	}

	public void update(Customer customer) {
		getHibernateTemplate().update(customer);
	}

	public void delete(Customer customer) {
		getHibernateTemplate().delete(customer);	
	}

	public Customer findById(Integer id) {
		List list = getHibernateTemplate().find("from Customer where id=?",id);
		if (CollectionUtils.isNotEmpty(list)) {
			return (Customer)list.get(0);
		} 
		return null;
	}

	public List<Customer> findAll() {
//		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		List<Customer> list = getHibernateTemplate().find("from Customer");
		return list;
	}

	public List<Customer> findLikeName(String name) {
		List<Customer> list = getHibernateTemplate().find("from Customer where name like '%" + name + "%'");
		return list;
	}

}

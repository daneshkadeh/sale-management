package com.hbsoft.ssm.dao;

import java.util.List;

import com.hbsoft.ssm.entity.Customer;

public interface CustomerDao {
	void save(Customer customer);
	void update(Customer customer);
	void delete(Customer customer);
	Customer findById(Integer id);
	List<Customer> findAll();
	List<Customer> findLikeName(String name);
}

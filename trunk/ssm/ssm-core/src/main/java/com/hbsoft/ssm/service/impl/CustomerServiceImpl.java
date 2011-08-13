package com.hbsoft.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.ssm.dao.CustomerDao;
import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDao customerDao;
	
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	public void update(Customer customer) {
		customerDao.update(customer);
	}

	public void delete(Customer customer) {
		customerDao.delete(customer);

	}

	public Customer findById(Integer id) {
		return customerDao.findById(id);
	}

	public List<Customer> findAll() {
		return customerDao.findAll();
	}

	public List<Customer> findLikeName(String name) {
		return customerDao.findLikeName(name);
	}

}

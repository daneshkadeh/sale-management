package com.s3s.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3s.ssm.dao.impl.CustomerDaoImpl;
import com.s3s.ssm.entity.Customer;
import com.s3s.ssm.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDaoImpl customerDao;

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

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

    @Override
    public void save(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);

    }

    public Customer findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public List<Customer> findLikeName(String name) {
        return customerDao.findLikeName(name);
    }

    @Override
    public Customer findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

}

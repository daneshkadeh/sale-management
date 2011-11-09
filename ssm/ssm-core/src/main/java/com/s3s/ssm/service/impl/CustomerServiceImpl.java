package com.s3s.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dao.impl.CustomerDaoImpl;
import com.s3s.ssm.entity.CustomerTest;
import com.s3s.ssm.service.CustomerService;

@Transactional
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDaoImpl customerDao;

    @Override
    public void save(CustomerTest customer) {
        customerDao.save(customer);
    }

    @Override
    public void update(CustomerTest customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(CustomerTest customer) {
        customerDao.delete(customer);

    }

    public CustomerTest findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<CustomerTest> findAll() {
        return customerDao.findAll();
    }

    @Override
    public List<CustomerTest> findLikeName(String name) {
        return customerDao.findLikeName(name);
    }

    @Override
    public CustomerTest findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

}

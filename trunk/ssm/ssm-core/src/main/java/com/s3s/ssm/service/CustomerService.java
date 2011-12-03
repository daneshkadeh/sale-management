package com.s3s.ssm.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.CustomerTest;

public interface CustomerService {
    @Transactional(propagation = Propagation.REQUIRED)
    void save(CustomerTest customer);

    @Transactional(propagation = Propagation.REQUIRED)
    void update(CustomerTest customer);

    @Transactional(propagation = Propagation.REQUIRED)
    void delete(CustomerTest customer);

    @Transactional(propagation = Propagation.SUPPORTS)
    CustomerTest findById(Integer id);

    @Transactional(propagation = Propagation.SUPPORTS)
    List<CustomerTest> findAll();

    @Transactional(propagation = Propagation.SUPPORTS)
    List<CustomerTest> findLikeName(String name);
}

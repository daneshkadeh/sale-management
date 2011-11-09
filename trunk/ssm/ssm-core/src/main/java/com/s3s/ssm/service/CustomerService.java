package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.entity.CustomerTest;

public interface CustomerService {
    void save(CustomerTest customer);

    void update(CustomerTest customer);

    void delete(CustomerTest customer);

    CustomerTest findById(Integer id);

    List<CustomerTest> findAll();

    List<CustomerTest> findLikeName(String name);
}

package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.entity.Customer;

public interface CustomerService {
    void save(Customer customer);

    void update(Customer customer);

    void delete(Customer customer);

    Customer findById(Integer id);

    List<Customer> findAll();

    List<Customer> findLikeName(String name);
}

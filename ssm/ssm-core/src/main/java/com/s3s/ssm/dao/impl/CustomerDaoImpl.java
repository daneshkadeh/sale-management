package com.s3s.ssm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.s3s.ssm.entity.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends HibernateBaseDaoImpl<Customer> {

    public List<Customer> findLikeName(String name) {
        List<Customer> list = getHibernateTemplate().find("from Customer where name like '%" + name + "%'");
        return list;
    }
}
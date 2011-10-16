package com.s3s.ssm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.s3s.ssm.entity.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends HibernateBaseDaoImpl<Customer> {

    public List<Customer> findLikeName(String name) {
        DetachedCriteria dc = getCriteria(Customer.class);
        dc.add(Restrictions.like("name", name));
        return findByCriteria(dc);
    }
}

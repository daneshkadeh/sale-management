package com.s3s.ssm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.s3s.ssm.entity.CustomerTest;

@Repository("customerDao")
public class CustomerDaoImpl extends BaseDaoImpl<CustomerTest> {

    public List<CustomerTest> findLikeName(String name) {
        DetachedCriteria dc = getCriteria();
        dc.add(Restrictions.like("name", name));
        return findByCriteria(dc);
    }
}

/*
 * CustomerDaoImpl
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
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

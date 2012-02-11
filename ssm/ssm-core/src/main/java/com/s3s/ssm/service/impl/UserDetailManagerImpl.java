/*
 * UserDetailManagerImpl
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
package com.s3s.ssm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.util.ConfigProvider;

public class UserDetailManagerImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        IBaseDao<User> userDao = (IBaseDao<User>) ConfigProvider.getInstance().getDaoHelper()
                .getDao(User.class);
        DetachedCriteria dc = DetachedCriteria.forClass(User.class).add(Property.forName("username").eq(username));

        List<User> userDetailsList = userDao.findByCriteria(dc);

        if (userDetailsList.size() == 0) {
            throw new UsernameNotFoundException(username + " not found");
        } else {
            return userDetailsList.get(0);
        }
    }
}

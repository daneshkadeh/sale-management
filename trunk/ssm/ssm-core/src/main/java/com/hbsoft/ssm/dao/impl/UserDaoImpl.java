package com.hbsoft.ssm.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.entity.LoginConfig;
import com.hbsoft.ssm.entity.User;

@Repository("userDao")
public class UserDaoImpl extends HibernateBaseDaoImpl<User> {
	public User findByUsername(String username) {
        List<User> users = getHibernateTemplate().find("from User where username = ?", username);
        if (CollectionUtils.isNotEmpty(users)) {
            return (User) users.get(0);
        }
        return null;
    }
}

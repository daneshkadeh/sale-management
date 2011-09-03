package com.hbsoft.ssm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.entity.LoginConfig;

@Repository("loginConfigDao")
public class LoginConfigDaoImpl extends HibernateBaseDaoImpl<LoginConfig> {
	
	public List<LoginConfig> findByAppName(String appName) {
        List<LoginConfig> list = getHibernateTemplate().find("from LoginConfig where appName = ?", appName);
        return list;
    }
	
}

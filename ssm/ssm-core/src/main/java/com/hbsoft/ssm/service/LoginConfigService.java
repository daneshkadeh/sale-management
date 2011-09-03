package com.hbsoft.ssm.service;

import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;

import com.hbsoft.ssm.entity.LoginConfig;

public interface LoginConfigService {
	
	void save(LoginConfig loginConfig);
	
	void save(String appName, AppConfigurationEntry entry);
	
	void update(LoginConfig loginConfig);

	void delete(LoginConfig loginConfig);

	LoginConfig findById(Integer id);
	
	List<LoginConfig> findByAppName(String appName);
}

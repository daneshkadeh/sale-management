package com.s3s.ssm.service;

import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;

import com.s3s.ssm.entity.LoginConfig;

public interface LoginConfigService {

    void save(String appName, AppConfigurationEntry entry);

    List<LoginConfig> findByAppName(String appName);
}

package com.s3s.ssm.service.impl;

import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3s.ssm.dao.impl.LoginConfigDaoImpl;
import com.s3s.ssm.entity.LoginConfig;
import com.s3s.ssm.service.LoginConfigService;

@Service("loginConfigService")
public class LoginConfigServiceImpl implements LoginConfigService {

    @Autowired
    LoginConfigDaoImpl loginConfigDaoImpl;

    public LoginConfig findById(Long id) {
        return loginConfigDaoImpl.findById(id);
    }

    @Override
    public void save(String appName, AppConfigurationEntry entry) {
        LoginConfig loginConfig = new LoginConfig();
        loginConfig.setAppName(appName);
        loginConfig.setLoginModuleClass(entry.getLoginModuleName());
        loginConfig.setControlFlag(controlFlagString(entry.getControlFlag()));
        loginConfigDaoImpl.save(loginConfig);
    }

    private String controlFlagString(LoginModuleControlFlag flag) {
        if (LoginModuleControlFlag.REQUIRED.equals(flag)) {
            return "REQUIRED";
        } else if (LoginModuleControlFlag.REQUISITE.equals(flag)) {
            return "REQUISITE";
        } else if (LoginModuleControlFlag.SUFFICIENT.equals(flag)) {
            return "SUFFICIENT";
        } else {
            return "OPTIONAL";
        }

    }

    @Override
    public List<LoginConfig> findByAppName(String appName) {
        // TODO Auto-generated method stub
        return loginConfigDaoImpl.findByAppName(appName);
    }
}

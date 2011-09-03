package com.hbsoft.ssm.service.impl;

import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.ssm.dao.impl.LoginConfigDaoImpl;
import com.hbsoft.ssm.entity.LoginConfig;
import com.hbsoft.ssm.service.LoginConfigService;

@Service("loginConfigService")
public class LoginConfigServiceImpl implements LoginConfigService {
	
	@Autowired
    LoginConfigDaoImpl loginConfigDaoImpl;
	
	public void save(LoginConfig loginConfig) {
		// TODO Auto-generated method stub
		loginConfigDaoImpl.save(loginConfig);
	}

	public void update(LoginConfig loginConfig) {
		loginConfigDaoImpl.update(loginConfig);
	}

	public void delete(LoginConfig loginConfig) {
		loginConfigDaoImpl.delete(loginConfig);
	}

	public LoginConfig findById(Integer id) {
		return loginConfigDaoImpl.findById(id);
	}

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

	public List<LoginConfig> findByAppName(String appName) {
		// TODO Auto-generated method stub
		return loginConfigDaoImpl.findByAppName(appName);
	}
}

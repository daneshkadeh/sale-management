package com.s3s.ssm.security;

import java.security.Provider;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.Configuration;

import com.s3s.ssm.entity.LoginConfig;
import com.s3s.ssm.service.LoginConfigService;
import com.s3s.ssm.util.ConfigProvider;

public class LoginConfiguration extends Configuration {
    static private LoginConfiguration dbConfig;
    private LoginConfigService loginConfigService = ConfigProvider.getInstance().getLoginConfigService();

    static public void init() {
        dbConfig = new LoginConfiguration();
        Configuration.setConfiguration(dbConfig);
    }

    static public LoginConfiguration getLoginConfiguration() {
        return dbConfig;
    }

    public void addAppConfigurationEntry(String appName, AppConfigurationEntry entry) throws SQLException {
        // insert an entry into the database for the LoginModule
        // indicated by the passed in AppConfigurationEntry
        loginConfigService.save(appName, entry);
    }

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String appName) {
        if (appName == null) {
            throw new NullPointerException("applicationName passed in was null");
        }
        List<AppConfigurationEntry> entries = new ArrayList<AppConfigurationEntry>();
        String loginModuleClass;
        String controlFlagValue;
        AppConfigurationEntry.LoginModuleControlFlag controlFlag;
        HashMap<String, ?> options;

        List<LoginConfig> loginConfigList = loginConfigService.findByAppName(appName);

        for (LoginConfig loginConfig : loginConfigList) {
            loginModuleClass = loginConfig.getLoginModuleClass();
            controlFlagValue = loginConfig.getControlFlag();
            controlFlag = resolveControlFlag(controlFlagValue);
            options = new HashMap();
            AppConfigurationEntry entry = new AppConfigurationEntry(loginModuleClass, controlFlag, options);
            entries.add(entry);
        }
        return (AppConfigurationEntry[]) entries.toArray(new AppConfigurationEntry[entries.size()]);

    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        super.refresh();
    }

    @Override
    public Parameters getParameters() {
        // TODO Auto-generated method stub
        return super.getParameters();
    }

    @Override
    public Provider getProvider() {
        // TODO Auto-generated method stub
        return super.getProvider();
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return super.getType();
    }

    private LoginModuleControlFlag resolveControlFlag(String name) {
        if (name == null) {
            throw new NullPointerException("control flag name passed in is null.");
        }

        String uppedName = name.toUpperCase(Locale.US);
        if ("REQUIRED".equals(uppedName)) {
            return LoginModuleControlFlag.REQUIRED;
        } else if ("REQUISITE".equals(uppedName)) {
            return LoginModuleControlFlag.REQUISITE;
        } else if ("SUFFICIENT".equals(uppedName)) {
            return LoginModuleControlFlag.SUFFICIENT;
        } else if ("OPTIONAL".equals(uppedName)) {
            return LoginModuleControlFlag.OPTIONAL;
        } else {
            // default if unknown
            return AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
        }
    }
}
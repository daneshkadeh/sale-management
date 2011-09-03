package com.hbsoft.ssm.security;

import java.security.Provider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.Configuration;

import com.hbsoft.ssm.security.DBUtil;

public class HBConfiguration extends Configuration {
    static private HBConfiguration dbConfig;

    static public void init() {
        dbConfig = new HBConfiguration();
        Configuration.setConfiguration(dbConfig);
    }

    static public HBConfiguration getDbConfiguration() {
        return dbConfig;
    }

    public void addAppConfigurationEntry(String appName, AppConfigurationEntry entry) throws SQLException {
        // insert an entry into the database for the LoginModule
        // indicated by the passed in AppConfigurationEntry
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO tbl_app_configuration VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, appName);
            pstmt.setString(2, entry.getLoginModuleName());
            pstmt.setString(3, controlFlagString(entry.getControlFlag()));
            pstmt.executeUpdate();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String applicationName) {
        if (applicationName == null) {
            throw new NullPointerException("applicationName passed in was null");
        }
        Connection connection = DBUtil.getConnection();
        String sql = "SELECT loginModuleClass, controlFlag " + "FROM tbl_app_configuration WHERE appName=?";
        List entries = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, applicationName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String loginModuleClass = rs.getString("loginModuleClass");
                String controlFlagValue = rs.getString("controlFlag");
                AppConfigurationEntry.LoginModuleControlFlag controlFlag = resolveControlFlag(controlFlagValue);
                AppConfigurationEntry entry = new AppConfigurationEntry(loginModuleClass, controlFlag, new HashMap());
                entries.add(entry);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    LoginModuleControlFlag resolveControlFlag(String name) {
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

    static String controlFlagString(LoginModuleControlFlag flag) {
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
}

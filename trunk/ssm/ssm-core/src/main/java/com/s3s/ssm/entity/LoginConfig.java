package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_auth_login")
public class LoginConfig extends AbstractBaseIdObject {

    private String appName;
    private String loginModuleClass;
    private String controlFlag;

    @Column(name = "appName", nullable = false)
    @NotNull
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Column(name = "loginModuleClass", nullable = false)
    @NotNull
    public String getLoginModuleClass() {
        return loginModuleClass;
    }

    public void setLoginModuleClass(String loginModuleClass) {
        this.loginModuleClass = loginModuleClass;
    }

    @Column(name = "controlFlag", nullable = false)
    @NotNull
    public String getControlFlag() {
        return controlFlag;
    }

    public void setControlFlag(String controlFlag) {
        this.controlFlag = controlFlag;
    }
}

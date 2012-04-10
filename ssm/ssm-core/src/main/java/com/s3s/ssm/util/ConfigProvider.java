/*
 * ConfigProvider
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
package com.s3s.ssm.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.AuthenticationManager;

import com.s3s.ssm.context.ContextProvider;
import com.s3s.ssm.security.CustomJdbcMutableAclService;
import com.s3s.ssm.service.CustomerService;
import com.s3s.ssm.service.DetailInvoiceService;
import com.s3s.ssm.service.GoodsService;

public class ConfigProvider {
    private static ConfigProvider configProvider;
    private static ApplicationContext appContext;
    private static ApplicationContext authContext;

    public static ConfigProvider getInstance() {
        if (configProvider == null) {
            configProvider = new ConfigProvider();
        }
        return configProvider;
    }

    private ConfigProvider() {
        // DOMConfigurator.configure("log4j.xml");
        appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        authContext = new ClassPathXmlApplicationContext(new String[] { "security/auth-context.xml",
                "security/acl-context.xml" });
    }

    public ApplicationContext getApplicationContext() {
        return appContext;
    }

    public ApplicationContext getAuthenticationContext() {
        return authContext;
    }

    public CustomerService getCustomerSerice() {
        return (CustomerService) appContext.getBean("customerService");
    }

    public GoodsService getGoodsService() {
        return (GoodsService) appContext.getBean("goodsService");
    }

    public DetailInvoiceService getDetailInvoiceService() {
        return (DetailInvoiceService) appContext.getBean("detailInvoiceService");
    }

    public DaoHelper getDaoHelper() {
        return (DaoHelper) appContext.getBean("daoHelper");
    }

    public AuthenticationManager getAuthenticationManager() {
        return (AuthenticationManager) authContext.getBean("authenticationManager");
    }

    public CustomJdbcMutableAclService getMutableAclService() {
        return (CustomJdbcMutableAclService) authContext.getBean("aclService");
    }

    public ContextProvider getContextProvider() {
        return (ContextProvider) appContext.getBean("contextProvider");
    }

    public ServiceProvider getServiceProvider() {
        return (ServiceProvider) appContext.getBean("serviceProvider");
    }

    public CacheDataService getCacheDataService() {
        return (CacheDataService) appContext.getBean("cacheDataService");
    }
}

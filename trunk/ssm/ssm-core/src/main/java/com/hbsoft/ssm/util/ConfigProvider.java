package com.hbsoft.ssm.util;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hbsoft.ssm.service.CustomerService;
import com.hbsoft.ssm.service.DetailInvoiceService;
import com.hbsoft.ssm.service.GoodsService;
import com.hbsoft.ssm.service.InvoiceService;
import com.hbsoft.ssm.service.LoginConfigService;
import com.hbsoft.ssm.service.PermissionEntityService;
import com.hbsoft.ssm.service.PrincipalEntityService;
import com.hbsoft.ssm.service.UserService;

public class ConfigProvider {
    private static ConfigProvider configProvider;
    private static ApplicationContext appContext;

    public static ConfigProvider getInstance() {
        if (configProvider == null) {
            configProvider = new ConfigProvider();
        }
        return configProvider;
    }

    public ConfigProvider() {
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
    }

    public ApplicationContext getApplicationContext() {
        return appContext;
    }

    public CustomerService getCustomerSerice() {
        return (CustomerService) appContext.getBean("customerService");
    }

    public GoodsService getGoodsService() {
        return (GoodsService) appContext.getBean("goodsService");
    }

    public InvoiceService getInvoiceService() {
        return (InvoiceService) appContext.getBean("invoiceService");
    }

    public DetailInvoiceService getDetailInvoiceService() {
        return (DetailInvoiceService) appContext.getBean("detailInvoiceService");
    }

    public LoginConfigService getLoginConfigService() {
        return (LoginConfigService) appContext.getBean("loginConfigService");
    }

    public UserService getUserService() {
        return (UserService) appContext.getBean("userService");
    }

    public PermissionEntityService getPermissionEntityService() {
        return (PermissionEntityService) appContext.getBean("permissionEntityService");
    }

    public PrincipalEntityService getPrincipalEntityService() {
        return (PrincipalEntityService) appContext.getBean("principalEntityService");
    }
}

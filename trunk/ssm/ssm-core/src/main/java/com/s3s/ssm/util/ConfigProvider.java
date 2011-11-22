package com.s3s.ssm.util;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.s3s.ssm.context.ContextProvider;
import com.s3s.ssm.service.CustomerService;
import com.s3s.ssm.service.DetailInvoiceService;
import com.s3s.ssm.service.GoodsService;
import com.s3s.ssm.service.InvoiceService;
import com.s3s.ssm.service.LoginConfigService;
import com.s3s.ssm.service.PermissionEntityService;
import com.s3s.ssm.service.PrincipalEntityService;
import com.s3s.ssm.service.UserService;

public class ConfigProvider {
    private static ConfigProvider configProvider;
    private static ApplicationContext appContext;

    public static ConfigProvider getInstance() {
        if (configProvider == null) {
            configProvider = new ConfigProvider();
        }
        return configProvider;
    }

    private ConfigProvider() {
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

    public DaoHelper getDaoHelper() {
        return (DaoHelper) appContext.getBean("daoHelper");
    }

    public ContextProvider getContextProvider() {
        return (ContextProvider) appContext.getBean("contextProvider");
    }
}

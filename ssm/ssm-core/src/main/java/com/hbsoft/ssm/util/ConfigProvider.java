package com.hbsoft.ssm.util;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hbsoft.ssm.service.CustomerService;
import com.hbsoft.ssm.service.DetailInvoiceService;
import com.hbsoft.ssm.service.GoodsService;
import com.hbsoft.ssm.service.InvoiceService;

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
}

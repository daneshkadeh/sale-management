package com.hbsoft.ssm.program;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hbsoft.ssm.entity.Customer;
import com.hbsoft.ssm.entity.DetailInvoice;
import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.service.DetailInvoiceService;
import com.hbsoft.ssm.util.ConfigProvider;
import com.hbsoft.ssm.util.i18n.ControlConfiguration;
import com.hbsoft.ssm.view.CustomerView;
import com.hbsoft.ssm.view.EditGoodsView;
import com.hbsoft.ssm.view.ListGoodsView;

public class MainProgram {
	public static Log s_logger = LogFactory.getLog(MainProgram.class);
	private static final String MESSSAGE_FILE = "i18n/messages";
    public static void main( String[] args )
    {
    	ConfigProvider configProvider = ConfigProvider.getInstance();
    	ControlConfiguration.init();
    	ControlConfiguration.setLabelMessageBundle(Locale.FRENCH, MESSSAGE_FILE);
    	/*
    	s_logger.info("Testing project Hibernate-Spring-log4j-Swing");
    	Customer customer1 = new Customer();
    	customer1.setName("customer1");
    	configProvider.getCustomerSerice().save(customer1);
    	*/
    	
    	/*
    	Goods goods1 = new Goods();
    	goods1.setName("goods3");
    	goods1.setPriceBeforeTax(300.0);
    	goods1.setTax(3.0);
    	goods1.setPriceAfterTax(297.0);
    	goods1.setAddQuantity(4);
    	goods1.setCurQuantity(30);
    	configProvider.getGoodsService().save(goods1);
    	s_logger.info("Finish save new customer, new goods");
    	*/
    	/*
    	Invoice invoice = new Invoice();
    	invoice.setCreatedDate(new Date());
    	invoice.setCustomerId(1);
    	invoice.setTaxTotal(1.0);
    	invoice.setTotalBeforeTax(100.0);
    	invoice.setTotalAfterTax(99.0);
    	configProvider.getInvoiceService().save(invoice);*/
    	/*
    	DetailInvoice detailInvoice = new DetailInvoice();
    	detailInvoice.setGoodsId(1);
    	detailInvoice.setGoodsName("goods1");
    	detailInvoice.setInvoiceId(1);
    	detailInvoice.setQuantity(1);
    	detailInvoice.setMoneyAfterTax(99.0);
    	detailInvoice.setMoneyBeforeTax(100.0);
    	detailInvoice.setMoneyOfTax(1.0);
    	detailInvoice.setPriceAfterTax(99.0);
    	detailInvoice.setPriceBeforeTax(100.0);
    	detailInvoice.setTax(1.0);
    	configProvider.getDetailInvoiceService().save(detailInvoice);
    	*/
    	
    	
    	java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
//                new CustomerView().setVisible(true);
            	new EditGoodsView().setVisible(true);
//            	new ListGoodsView().setVisible(true);
            }
        });
        
	}
}


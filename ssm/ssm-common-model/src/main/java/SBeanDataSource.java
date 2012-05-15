/*
 * InvoiceBeanSource
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

import java.util.Arrays;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.s3s.ssm.entity.sales.DetailInvoice;

/**
 * @author Phan Hong Phuc
 * @since May 13, 2012
 */
public class SBeanDataSource extends JRAbstractBeanDataSourceProvider {

    /**
     * @param beanClass
     */
    public SBeanDataSource() {
        super(DetailInvoice.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JRDataSource create(JasperReport report) throws JRException {
        DetailInvoice di = new DetailInvoice();
        di.setAmount(5);
        di.setProductName("productName1");
        DetailInvoice di1 = new DetailInvoice();
        di.setAmount(2);
        di.setProductName("productName2");
        DetailInvoice di2 = new DetailInvoice();
        di.setAmount(4);
        di.setProductName("productName3");

        return new JRBeanCollectionDataSource(Arrays.asList(di, di1, di2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(JRDataSource dataSource) throws JRException {
    }

}

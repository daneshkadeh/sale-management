package com.s3s.ssm.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.interfaces.report.IReportService;

/*
 * ReportServiceImpl
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

/**
 * @author Phan Hong Phuc
 * @since Mar 27, 2012
 */
@Transactional
@Service("ReportServiceImpl")
public class ReportServiceImpl extends AbstractModuleServiceImpl implements IReportService {

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @PostConstruct
    @Override
    public void init() {
        serviceProvider.register(IReportService.class, this);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     * @throws JRException
     */
    @Override
    public JasperPrint getBankingReport() throws JRException, SQLException {
        return getReport("/reports/report2.jasper", new HashMap<String, Object>());
    }

    @Override
    public JasperPrint getInvoicePrint(Long invoiceId) throws JRException, SQLException {
        Invoice invoice = getDaoHelper().getDao(Invoice.class).findById(invoiceId);
        Map<String, Object> param = new HashMap<>();
        param.put("INVOICE_ID", invoiceId);
        param.put("INVOICE_CODE", invoice.getInvoiceNumber());
        param.put("INVOICE_DATE", invoice.getCreatedDate());
        // DetailInvoice di = new DetailInvoice();
        // di.setAmount(5);
        // di.setProductName("productName1");
        // DetailInvoice di1 = new DetailInvoice();
        // di1.setAmount(2);
        // di1.setProductName("productName2");
        // DetailInvoice di2 = new DetailInvoice();
        // di2.setAmount(4);
        // di2.setProductName("productName3");
        //
        // JRDataSource ds = new JRBeanCollectionDataSource(Arrays.asList(di, di1, di2));

        return getReport("/reports/invoice.jasper", param);

    }

    private JasperPrint getReport(String filePath, Map<String, Object> reportParameters) throws JRException,
            SQLException {
        return JasperFillManager.fillReport(getClass().getResourceAsStream(filePath), reportParameters,
                dataSource.getConnection());
    }

    private JasperPrint getReport(String filePath, Map<String, Object> reportParameters, JRDataSource ds)
            throws JRException, SQLException {
        return JasperFillManager.fillReport(getClass().getResourceAsStream(filePath), reportParameters, ds);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}

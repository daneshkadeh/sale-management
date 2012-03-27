/*
 * SSMReportViewer
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

package com.s3s.ssm.view;

import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 * @author Phan Hong Phuc
 * @since Mar 28, 2012
 */
public abstract class SSMReportViewer extends JRViewer {

    /**
     * @param jrPrint
     */
    public SSMReportViewer(JasperPrint jrPrint) {
        super(jrPrint);
        viewerContext.loadReport(getJasperPrint());
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initViewerContext(Locale locale, ResourceBundle resBundle) {
        viewerContext = new SSMReportViewerController(this, locale, resBundle);
        setLocale(viewerContext.getLocale());
        viewerContext.addListener(this);
    }

    /**
     * This function is called to get JasperPrint everytime the report reloaded.
     * 
     * @return
     */
    abstract protected JasperPrint getJasperPrint();
}

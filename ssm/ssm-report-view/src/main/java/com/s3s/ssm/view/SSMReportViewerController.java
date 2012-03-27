/*
 * SSMReportViewerController
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

import net.sf.jasperreports.swing.JRViewerController;

/**
 * @author Phan Hong Phuc
 * @since Mar 28, 2012
 */
public class SSMReportViewerController extends JRViewerController {

    protected SSMReportViewer ssmReportViewer;

    /**
     * @param ssmReportViewer
     * @param locale
     * @param resBundle
     */
    public SSMReportViewerController(SSMReportViewer ssmReportViewer, Locale locale, ResourceBundle resBundle) {
        super(locale, resBundle);
        this.ssmReportViewer = ssmReportViewer;
    }

    @Override
    public boolean isReloadSupported() {
        return true;
    }

    @Override
    public void reload() {
        loadReport(ssmReportViewer.getJasperPrint());
        forceRefresh();
    }

}

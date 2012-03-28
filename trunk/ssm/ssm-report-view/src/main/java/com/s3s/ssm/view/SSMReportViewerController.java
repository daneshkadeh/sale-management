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
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewerController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Phan Hong Phuc
 * @since Mar 28, 2012
 */
public class SSMReportViewerController extends JRViewerController {

    private static final Log logger = LogFactory.getLog(SSMReportViewerController.class);
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
        ssmReportViewer.getBusyPane().setBusy(true);
        new SwingWorker<JasperPrint, Object>() {

            @Override
            protected JasperPrint doInBackground() throws Exception {
                return ssmReportViewer.getJasperPrint();
            }

            @Override
            protected void done() {
                try {
                    loadReport(get());
                    forceRefresh();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error(e.getMessage());
                    JOptionPane.showMessageDialog(ssmReportViewer,
                            ControlConfigUtils.getString("error.refreshData.message"),
                            ControlConfigUtils.getString("error.title"), JOptionPane.ERROR_MESSAGE, null);
                } finally {
                    ssmReportViewer.getBusyPane().setBusy(false);
                }
            }
        }.execute();
    }

}

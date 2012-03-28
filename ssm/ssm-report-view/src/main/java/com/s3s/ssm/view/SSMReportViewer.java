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

import javax.swing.JPanel;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import org.divxdede.swing.busy.DefaultBusyModel;
import org.divxdede.swing.busy.JBusyComponent;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * The report viewer supporting load report dynamically through the supported function getJasperPrint().
 * 
 * @author Phan Hong Phuc
 * @since Mar 28, 2012
 */
public abstract class SSMReportViewer extends JRViewer implements IViewLazyLoadable {
    private static final long serialVersionUID = 4125751301041408777L;
    private boolean isInitialized = false;
    protected JBusyComponent<JPanel> busyPane;

    public SSMReportViewer() {
        super(null);
        JPanel mainPanel = (JPanel) getComponent(1);
        busyPane = createBusyPane(mainPanel);
        add(busyPane, 1);
    }

    private JBusyComponent<JPanel> createBusyPane(JPanel panel) {
        JBusyComponent<JPanel> bp = new JBusyComponent<>((JPanel) panel);
        ((DefaultBusyModel) bp.getBusyModel()).setDescription(ControlConfigUtils.getString("label.report.loading"));
        return bp;
    }

    @Override
    protected void initViewerContext(Locale locale, ResourceBundle resBundle) {
        viewerContext = new SSMReportViewerController(this, locale, resBundle);
        setLocale(viewerContext.getLocale());
        viewerContext.addListener(this);
    }

    @Override
    public void loadView() {
        if (!isInitialized) {
            viewerContext.reload();
            isInitialized = true;
        }
    }

    public JBusyComponent<JPanel> getBusyPane() {
        return busyPane;
    }

    /**
     * This function is called to get JasperPrint everytime the report reloaded.
     * 
     * @return
     */
    abstract protected JasperPrint getJasperPrint();
}

/*
 * ReportDomain
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
package com.s3s.ssm.view.domain;

import java.sql.SQLException;

import javax.swing.JScrollPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import com.s3s.ssm.interfaces.report.IReportService;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;
import com.s3s.ssm.util.ServiceProvider;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.SSMReportViewer;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.statistic.sales.ListUnsoldProductStatistic;
import com.s3s.ssm.view.statistic.store.ListCustDebtHistoryView;
import com.s3s.ssm.view.statistic.store.ListImportStoreReportDataView;
import com.s3s.ssm.view.statistic.store.ListProductInStoreStatistic;
import com.s3s.ssm.view.statistic.store.ListSupDebtHistoryView;

/**
 * All views of report and warning alerts.
 * 
 * @author phamcongbang
 * 
 */
public class ReportDomain extends AbstractDomain {
    private static final long serialVersionUID = 6771751960199984966L;

    public ReportDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Report.Management"));
        setIcon(IziImageUtils.getMediumIcon(IziImageConstants.REPORT_ICON));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView baoDongKichCauNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.KichCau")); // "Bao dong kich cau"
        TreeNodeWithView baoDongHetHangNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.HetHang")); // "Bao dong het hang"
        TreeNodeWithView hangBanChayNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.HangBanChay")); // "Hang ban chay"
        TreeNodeWithView hangTonQuaLauNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.HangTonLau")); // "Hang ton qua lau"
        hangTonQuaLauNode.setView(new ListUnsoldProductStatistic());
        TreeNodeWithView thongKeHangBanNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.ThongKeHangBan")); // "Thong ke hang ban"
        TreeNodeWithView thongKeDoanhThuChiPhiNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.DoanhThuChiPhi")); // "Thong ke doanh thu - chi phi"

        // //////////Test

        TreeNodeWithView reportTestNode = new TreeNodeWithView("Test report"); // "Thong ke doanh thu - chi phi"

        final ServiceProvider sp = ConfigProvider.getInstance().getServiceProvider();
        JRViewer jviewer = new SSMReportViewer() {
            private static final long serialVersionUID = 3700658325020850865L;

            @Override
            protected JasperPrint getJasperPrint() {
                try {
                    return sp.getService(IReportService.class).getInvoicePrint(1L);
                } catch (JRException | SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        reportTestNode.setView(jviewer);

        TreeNodeWithView importStoreNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.ImportStore"));
        importStoreNode.setView(new ListImportStoreReportDataView());

        TreeNodeWithView custDebtHistNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.custDebtHistory"));
        custDebtHistNode.setView(new ListCustDebtHistoryView());

        TreeNodeWithView supDebtHistNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.supDebtHistory"));
        supDebtHistNode.setView(new ListSupDebtHistoryView());

        // Manage Unsold product
        TreeNodeWithView unsoldProductNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.UnsoldProductStatistic"));
        unsoldProductNode.setView(new ListProductInStoreStatistic());

        rootNode.add(baoDongKichCauNode);
        rootNode.add(baoDongHetHangNode);
        rootNode.add(hangBanChayNode);
        rootNode.add(hangTonQuaLauNode);
        rootNode.add(thongKeHangBanNode);
        rootNode.add(thongKeDoanhThuChiPhiNode);
        rootNode.add(importStoreNode);
        rootNode.add(custDebtHistNode);
        rootNode.add(supDebtHistNode);
        rootNode.add(unsoldProductNode);
        rootNode.add(reportTestNode);
    }

}

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
import com.s3s.ssm.view.statistic.contact.ListCustEncouragementStatistic;
import com.s3s.ssm.view.statistic.contact.ListLongDebtCustStatistic;
import com.s3s.ssm.view.statistic.sales.ListTopProductInMonthStatistic;
import com.s3s.ssm.view.statistic.sales.ListUnsoldProductByDayStatistic;
import com.s3s.ssm.view.statistic.sales.ListUnsoldProductBySoldQtyStatistic;
import com.s3s.ssm.view.statistic.store.ListCustDebtHistoryView;
import com.s3s.ssm.view.statistic.store.ListImportStoreReportDataView;
import com.s3s.ssm.view.statistic.store.ListLowProductInStoreStatistic;
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
        TreeNodeWithView warningRoot = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.WarningRoot")); // "Bao dong"
        TreeNodeWithView statisticRoot = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.statisticRoot")); // "Thống kê - Báo cáo"

        TreeNodeWithView baoDongKichCauNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.KichCau")); // "Bao dong kich cau"
        TreeNodeWithView baoDongHetHangNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.HetHang")); // "Bao dong het hang"
        TreeNodeWithView hangBanChayNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.HangBanChay")); // "Hang ban chay"
        TreeNodeWithView unsoldProductByDayNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.UnsoldProductByDayNode")); // "Hang ton qua lau theo ngay"
        TreeNodeWithView unsoldProductBySoldQtyNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.UnsoldProductBySoldQtyNode")); // "Hang ton qua lau theo ngay"
        TreeNodeWithView longDebtCustNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.LongDebtCustNode"));
        TreeNodeWithView topProductInMonthNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.TopProductInMonthode"));
        TreeNodeWithView lowProductInStoreNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.LowProductInStoreNode"));

        TreeNodeWithView thongKeHangBanNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.ThongKeHangBan")); // "Thong ke hang ban"
        TreeNodeWithView thongKeDoanhThuChiPhiNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.DoanhThuChiPhi")); // "Thong ke doanh thu - chi phi"
        TreeNodeWithView importStoreNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.ImportStore"));
        TreeNodeWithView custDebtHistNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.custDebtHistory"));
        TreeNodeWithView supDebtHistNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.supDebtHistory"));
        // Manage Unsold product
        TreeNodeWithView unsoldProductNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.UnsoldProductStatistic"));
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
        unsoldProductByDayNode.setView(new ListUnsoldProductByDayStatistic());
        unsoldProductBySoldQtyNode.setView(new ListUnsoldProductBySoldQtyStatistic());
        baoDongKichCauNode.setView(new ListCustEncouragementStatistic());
        unsoldProductNode.setView(new ListProductInStoreStatistic());
        longDebtCustNode.setView(new ListLongDebtCustStatistic());
        importStoreNode.setView(new ListImportStoreReportDataView());
        custDebtHistNode.setView(new ListCustDebtHistoryView());
        supDebtHistNode.setView(new ListSupDebtHistoryView());
        topProductInMonthNode.setView(new ListTopProductInMonthStatistic());
        lowProductInStoreNode.setView(new ListLowProductInStoreStatistic());

        rootNode.add(warningRoot);
        warningRoot.add(baoDongKichCauNode);
        warningRoot.add(longDebtCustNode);
        warningRoot.add(unsoldProductByDayNode);
        warningRoot.add(unsoldProductBySoldQtyNode);
        warningRoot.add(baoDongHetHangNode);
        warningRoot.add(hangBanChayNode);
        warningRoot.add(topProductInMonthNode);
        warningRoot.add(lowProductInStoreNode);

        rootNode.add(statisticRoot);
        statisticRoot.add(thongKeHangBanNode);
        statisticRoot.add(thongKeDoanhThuChiPhiNode);
        statisticRoot.add(importStoreNode);
        statisticRoot.add(custDebtHistNode);
        statisticRoot.add(supDebtHistNode);
        statisticRoot.add(unsoldProductNode);
        statisticRoot.add(reportTestNode);
    }
}

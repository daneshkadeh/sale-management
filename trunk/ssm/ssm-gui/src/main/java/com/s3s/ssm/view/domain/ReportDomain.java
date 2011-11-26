package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;

/**
 * All views of report and warning alerts.
 * 
 * @author phamcongbang
 * 
 */
public class ReportDomain extends AbstractDomain {

    public ReportDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Report.Management"));
    }

    @Override
    protected void constructTreeView(TreeView treeView) {
        // Report
        TreeNodeWithView reportEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.Management")); // "Report"
        TreeNodeWithView baoDongKichCauNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.KichCau")); // "Bao dong kich cau"
        TreeNodeWithView baoDongHetHangNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Report.HetHang")); // "Bao dong het hang"
        TreeNodeWithView hangBanChayNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.HangBanChay")); // "Hang ban chay"
        TreeNodeWithView hangTonQuaLauNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.HangTonLau")); // "Hang ton qua lau"
        TreeNodeWithView thongKeHangBanNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.ThongKeHangBan")); // "Thong ke hang ban"
        TreeNodeWithView thongKeDoanhThuChiPhiNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Report.DoanhThuChiPhi")); // "Thong ke doanh thu - chi phi"

        reportEntry.add(baoDongKichCauNode);
        reportEntry.add(baoDongHetHangNode);
        reportEntry.add(hangBanChayNode);
        reportEntry.add(hangTonQuaLauNode);
        reportEntry.add(thongKeHangBanNode);
        reportEntry.add(thongKeDoanhThuChiPhiNode);

        treeView.setModel(new DefaultTreeModel(reportEntry));
    }

}

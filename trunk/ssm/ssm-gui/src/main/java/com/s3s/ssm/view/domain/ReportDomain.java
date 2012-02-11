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

import javax.swing.JScrollPane;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;

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
    protected void constructTreeView(TreeNodeWithView rootNode) {
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

        rootNode.add(baoDongKichCauNode);
        rootNode.add(baoDongHetHangNode);
        rootNode.add(hangBanChayNode);
        rootNode.add(hangTonQuaLauNode);
        rootNode.add(thongKeHangBanNode);
        rootNode.add(thongKeDoanhThuChiPhiNode);
    }

}

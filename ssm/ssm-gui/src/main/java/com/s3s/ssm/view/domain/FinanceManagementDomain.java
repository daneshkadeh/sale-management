/*
 * FinanceManagementDomain
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
import com.s3s.ssm.view.list.finance.ListPaymentContentView;

/**
 * All views relates to finance activity.
 * 
 * @author phamcongbang
 * 
 */
public class FinanceManagementDomain extends AbstractDomain {

    public FinanceManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Finance.Management"));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView paymentContentEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Finance.PaymentContent"), new ListPaymentContentView());
        rootNode.add(paymentContentEntry);
        // Financial management -TODO: not add views
        // TreeNodeWithView receiveFMEntry = new
        // TreeNodeWithView(ControlConfigUtils.getString("JTree.Finance.Receivable")); //
        // "Receivable financial management"
        // TreeNodeWithView loaiThanhToan = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.PaymentType"), new ListPaymentContentView());
        // TreeNodeWithView thuKemToaHangNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Receivable.PaymentWithInvoice")); // "Thu kem toa hang"
        // TreeNodeWithView thuTienHangNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Receivable.PaymentCustomer")); // "Thu tien hang"
        // TreeNodeWithView khachTraMuonTienNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Receivable.FromBorrow")); // "Khach tra tien muon"
        // TreeNodeWithView otherReceiveNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Receivable.Other")); // "Khoan thu khac"
        // TreeNodeWithView payFMEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Finance.Payable")); //
        // "Payable financial management"
        // TreeNodeWithView tamUngKHNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Payable.PayForCustomer")); // "Tam ung khach hang"
        // TreeNodeWithView chiMuaHangNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Payable.PayForSales")); // "Chi mua hang"
        // TreeNodeWithView choVayTienNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Payable.LoanMoney"));// "Cho vay tien"
        // TreeNodeWithView chiPhiKhacNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Payable.Other")); // "Chi phi khac"
        // rootNode.add(loaiThanhToan);
        // rootNode.add(receiveFMEntry);
        // rootNode.add(payFMEntry);
        // receiveFMEntry.add(thuKemToaHangNode);
        // receiveFMEntry.add(thuTienHangNode);
        // receiveFMEntry.add(khachTraMuonTienNode);
        // receiveFMEntry.add(otherReceiveNode);
        // payFMEntry.add(tamUngKHNode);
        // payFMEntry.add(chiMuaHangNode);
        // payFMEntry.add(choVayTienNode);
        // payFMEntry.add(chiPhiKhacNode);
        //
        // // Quan ly cong no - TODO: not add views
        // TreeNodeWithView quanLyCongNoEntry = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Debtor.Management")); // "Quan ly cong no"
        // TreeNodeWithView congNoKHNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Debtor.Customer")); // "Cong no khach hang"
        // TreeNodeWithView congNoNCCKhacNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Finance.Debtor.Supplier")); // "Cong no nha cung cap"
        // rootNode.add(quanLyCongNoEntry);
        // quanLyCongNoEntry.add(congNoKHNode);
        // quanLyCongNoEntry.add(congNoNCCKhacNode);
    }

}

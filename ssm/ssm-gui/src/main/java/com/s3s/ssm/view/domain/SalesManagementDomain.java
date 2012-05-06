/*
 * SalesManagementDomain
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
import com.s3s.ssm.view.list.sales.ListCommissionTypeView;
import com.s3s.ssm.view.list.sales.ListInvoiceRefundView;
import com.s3s.ssm.view.list.sales.ListInvoiceSupporteeView;
import com.s3s.ssm.view.list.sales.ListInvoiceView;

/**
 * Manage sales products, sales calendar.
 * 
 * @author phamcongbang
 * 
 */
public class SalesManagementDomain extends AbstractDomain {

    public SalesManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.SalesManagement"));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView listInvoiceEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesManagement.Invoice"), new ListInvoiceView());

        TreeNodeWithView listInvoiceRefundEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesManagement.InvoiceRefund"), new ListInvoiceRefundView());

        TreeNodeWithView listInvoiceSupporteeEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesManagement.InvoiceSupportee"), new ListInvoiceSupporteeView());

        TreeNodeWithView listCommissionTypeEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesManagement.CommissionType"), new ListCommissionTypeView());

        rootNode.add(listInvoiceEntry);
        rootNode.add(listInvoiceRefundEntry);
        rootNode.add(listInvoiceSupporteeEntry);
        rootNode.add(listCommissionTypeEntry);
    }

}

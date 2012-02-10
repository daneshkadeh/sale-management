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
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.TreeView;
import com.s3s.ssm.view.component.AbstractDomain;
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
    protected void constructTreeView(TreeView treeView) {
        // Sales management
        TreeNodeWithView salesManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesManagement"));
        TreeNodeWithView listInvoiceEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesManagement.Invoice"), new ListInvoiceView());

        salesManagementEntry.add(listInvoiceEntry);

        treeView.setModel(new DefaultTreeModel(salesManagementEntry));
    }

}

/*
 * BuyManagementDomain
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
import com.s3s.ssm.view.list.sales.ListAddedSCFeeTypeView;
import com.s3s.ssm.view.list.sales.ListContractDocumentView;
import com.s3s.ssm.view.list.sales.ListSalesContractView;

/**
 * Buy products activity of the institution
 * 
 * @author phamcongbang
 * 
 */
public class BuyManagementDomain extends AbstractDomain {

    public BuyManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.SalesContract"));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView buyContractNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.buyContract"), new ListSalesContractView()); // "Hop dong mua hang"

        TreeNodeWithView ttManageNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.TTmanagement"));
        TreeNodeWithView lcManageNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.LCmanagement"));
        TreeNodeWithView declareContractNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.importNote")); // "Quan ly to khai"
        TreeNodeWithView contractDocumentNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.contractDocument"), new ListContractDocumentView());
        TreeNodeWithView addedSCFeeTypeNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.addedSCFeeType"), new ListAddedSCFeeTypeView());

        rootNode.add(buyContractNode);
        rootNode.add(ttManageNode);
        rootNode.add(lcManageNode);
        rootNode.add(declareContractNode);
        rootNode.add(contractDocumentNode);
        rootNode.add(addedSCFeeTypeNode);
    }

}

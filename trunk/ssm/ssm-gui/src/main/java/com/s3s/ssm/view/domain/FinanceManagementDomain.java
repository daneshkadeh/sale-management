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

import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.list.finance.ListContractPaymentView;
import com.s3s.ssm.view.list.finance.ListInvoicePaymentView;
import com.s3s.ssm.view.list.finance.ListPaymentContentView;
import com.s3s.ssm.view.list.finance.ListPaymentView;
import com.s3s.ssm.view.list.finance.ListReceiptView;

/**
 * All views relates to finance activity.
 * 
 * @author phamcongbang
 * 
 */
public class FinanceManagementDomain extends AbstractDomain {
    private static final long serialVersionUID = 5291531871545628278L;

    public FinanceManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Finance.Management"));
        setIcon(ImageUtils.getMediumIcon(ImageConstants.MENU_FINANCE_ICON));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView paymentContentEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Finance.PaymentContent"), new ListPaymentContentView());
        TreeNodeWithView paymentEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Finance.Payment"),
                new ListPaymentView());
        TreeNodeWithView receiptEntry = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Finance.Receipt"),
                new ListReceiptView());
        TreeNodeWithView contractPaymentEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Finance.ContractPayment"), new ListContractPaymentView());

        TreeNodeWithView invoicePaymentEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Finance.InvoicePayment"), new ListInvoicePaymentView());
        rootNode.add(paymentContentEntry);
        rootNode.add(paymentEntry);
        rootNode.add(receiptEntry);
        rootNode.add(contractPaymentEntry);
        rootNode.add(invoicePaymentEntry);
    }

}

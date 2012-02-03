/*
 * ContactManagementDomain
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
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;
import com.s3s.ssm.view.list.contact.ListCustomerView;
import com.s3s.ssm.view.list.contact.ListPartnerCategoryView;
import com.s3s.ssm.view.list.contact.ListSupplierView;

/**
 * All views of contact.
 * 
 * @author phamcongbang
 * 
 */
public class ContactManagementDomain extends AbstractDomain {

    public ContactManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Contact.Management"));
    }

    @Override
    protected void constructTreeView(TreeView treeView) {
        // CRM Contact management
        TreeNodeWithView contactMagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.Management")); // "CRM contact management"
        TreeNodeWithView customerNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Contact.Customer"),
                new ListCustomerView()); // "Customer"
        TreeNodeWithView partnerCateNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.PartnerCategory"), new ListPartnerCategoryView()); // "Customer"
        TreeNodeWithView supplierNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.SupplierContact"), new ListSupplierView()); // "Supplier"
        TreeNodeWithView nguoiDuocTaiTroNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.SupportedContact")); // "Nguoi duoc tai tro"

        contactMagementEntry.add(customerNode);
        contactMagementEntry.add(supplierNode);
        contactMagementEntry.add(partnerCateNode);
        contactMagementEntry.add(nguoiDuocTaiTroNode);
        treeView.setModel(new DefaultTreeModel(contactMagementEntry));

    }

}
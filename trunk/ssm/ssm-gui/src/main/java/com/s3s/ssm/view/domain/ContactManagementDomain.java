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

import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.list.contact.ListAudienceCategoryView;
import com.s3s.ssm.view.list.contact.ListCustomerView;
import com.s3s.ssm.view.list.contact.ListPartnerCategoryView;
import com.s3s.ssm.view.list.contact.ListSupplierView;
import com.s3s.ssm.view.list.contact.ListSupporteeView;

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
        setIcon(ImageUtils.getMediumIcon(ImageConstants.USER_ICON));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        // CRM Contact management
        TreeNodeWithView customerNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Contact.Customer"),
                new ListCustomerView(), ImageUtils.getMediumIcon(ImageConstants.USER_ICON)); // "Customer"
        TreeNodeWithView partnerCateNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.PartnerCategory"), new ListPartnerCategoryView()); // "Customer"
        TreeNodeWithView supplierNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.SupplierContact"), new ListSupplierView()); // "Supplier"
        TreeNodeWithView nguoiDuocTaiTroNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.SupportedContact"), new ListSupporteeView()); // "Nguoi duoc tai tro"
        TreeNodeWithView audienceCateNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.AudienceCategory"), new ListAudienceCategoryView()); //

        rootNode.add(customerNode);
        rootNode.add(supplierNode);
        rootNode.add(partnerCateNode);
        rootNode.add(nguoiDuocTaiTroNode);
        rootNode.add(audienceCateNode);

    }
}

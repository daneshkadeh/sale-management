/*
 * EditMultiSupplierViewTest
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

package com.s3s.ssm.view.detail.contact;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.contact.ListPartnerAddressView;

/**
 * 
 * 
 * @author phamcongbang
 * 
 */
public class EditMultiSupplierView extends AbstractMultiEditView<Partner> {

    private static final long serialVersionUID = -427718510574119477L;

    public EditMultiSupplierView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Partner> constructMainView(TreeNodeWithView root, Partner entity,
            Map<String, Object> request) {
        EditSupplierGeneralView detailView = new EditSupplierGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView(
                ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Partner entity, Map<String, Object> request) {
        TreeNodeWithView nodeAddress = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SubMenu.Partner.Address"));
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListPartnerAddressView addressView = new ListPartnerAddressView(listRequest);
        nodeAddress.setView(addressView);
        root.add(nodeAddress);
    }

}

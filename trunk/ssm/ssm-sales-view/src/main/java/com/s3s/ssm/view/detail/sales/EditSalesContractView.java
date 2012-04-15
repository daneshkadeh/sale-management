/*
 * EditSalesContractView
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
package com.s3s.ssm.view.detail.sales;

import java.util.HashMap;
import java.util.Map;

import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.edit.AbstractMultiEditView;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.list.sales.ListDetailSalesContractView;
import com.s3s.ssm.view.list.sales.ListImportationSCView;

public class EditSalesContractView extends AbstractMultiEditView<SalesContract> {

    /**
     * 
     */
    private static final long serialVersionUID = -6238803319896329449L;

    public EditSalesContractView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected AbstractSingleEditView<SalesContract> constructMainView(TreeNodeWithView root, SalesContract entity,
            Map<String, Object> request) {
        EditSalesContractGeneralView detailView = new EditSalesContractGeneralView(request);
        TreeNodeWithView node = new TreeNodeWithView(
                ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, SalesContract entity, Map<String, Object> request) {
        TreeNodeWithView nodeItems = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SubMenu.EditSalesContractView.DetailSalesContract"));
        Map<String, Object> listRequest = new HashMap<>();
        listRequest.put(PARAM_PARENT_ID, entity.getId());
        listRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListDetailSalesContractView detailsView = new ListDetailSalesContractView(listRequest);
        nodeItems.setView(detailsView);
        root.add(nodeItems);

        TreeNodeWithView nodeImportations = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SubMenu.EditSalesContractView.ImportationSC"));
        Map<String, Object> importationRequest = new HashMap<>();
        importationRequest.put(PARAM_PARENT_ID, entity.getId());
        importationRequest.put(PARAM_PARENT_CLASS, entity.getClass());
        ListImportationSCView importationsView = new ListImportationSCView(importationRequest);
        nodeImportations.setView(importationsView);
        root.add(nodeImportations);
    }

    @Override
    protected String getDefaultTitle(SalesContract entity) {
        return entity.getCode();
    }

}

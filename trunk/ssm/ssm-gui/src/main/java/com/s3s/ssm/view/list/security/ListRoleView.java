/*
 * ListRoleView
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
package com.s3s.ssm.view.list.security;

import javax.swing.Icon;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.view.detail.security.EditRoleView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListRoleView extends AbstractListView<Role> {
    private static final long serialVersionUID = 7072683198560551663L;

    public ListRoleView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("active", ListRendererType.BOOLEAN);
    }

    @Override
    protected Class<? extends AbstractEditView<Role>> getEditViewClass() {
        return EditRoleView.class;
    }

    // @Override
    // public void showEditView(Role entity, EditActionEnum action) {
    // // TODO This call requires sub class override Constructor method! It's not good.
    // Map<String, Object> params = new HashMap<>();
    // params.put(PARAM_ENTITY_ID, entity.getId());
    // ACLPanel aclPanel = new ACLPanel(params);
    // aclPanel.setListView(this);
    // // TODO HPP consider to listen the event from AbstractDetailView (not set reference to it).
    // JScrollPane scrollPane = new JScrollPane(aclPanel);
    // Dimension preferredSize = aclPanel.getPreferredSize();
    // JFrame jFrame = WindowUtilities.openInJFrame(scrollPane, preferredSize.width + 100, preferredSize.height + 100);
    // WindowUtilities.centerOnScreen(jFrame);
    // }

}

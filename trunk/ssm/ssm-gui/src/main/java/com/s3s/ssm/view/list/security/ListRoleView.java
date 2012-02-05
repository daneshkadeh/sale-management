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

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.util.view.WindowUtils;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.security.EditRoleView;
import com.s3s.ssm.view.security.ACLPanel;

public class ListRoleView extends AbstractListView<Role> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isEnable", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractDetailView<Role>> getDetailViewClass() {
        return EditRoleView.class;
    }

    @Override
    protected void showDetailView(Role entity) {
        // TODO This call requires sub class override Constructor method! It's not good.
        ACLPanel aclPanel = new ACLPanel(entity);
        aclPanel.setListView(this);
        // TODO HPP consider to listen the event from AbstractDetailView (not set reference to it).
        JScrollPane scrollPane = new JScrollPane(aclPanel);
        Dimension preferredSize = aclPanel.getPreferredSize();
        JFrame jFrame = WindowUtils.openInJFrame(scrollPane, preferredSize.width + 100, preferredSize.height + 100);
        WindowUtils.centerOnScreen(jFrame);
    }

}

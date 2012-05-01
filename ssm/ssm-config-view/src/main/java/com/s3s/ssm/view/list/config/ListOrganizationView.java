/*
 * ListBasicInformationView
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
package com.s3s.ssm.view.list.config;

import javax.swing.Icon;

import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.view.detail.config.EditOrganizationView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListOrganizationView extends AListEntityView<Organization> {
    private static final long serialVersionUID = 4680054889190008669L;

    public ListOrganizationView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("address", ListRendererType.TEXT);
        listDataModel.addColumn("isDefault", ListRendererType.TEXT);
        listDataModel.addColumn("active", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Organization>> getEditViewClass() {
        return EditOrganizationView.class;
    }
}
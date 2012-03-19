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

import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.view.detail.config.EditOrganizationView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListOrganizationView extends AbstractListView<Organization> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("name", ListColumnType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<Organization>> getEditViewClass() {
        return EditOrganizationView.class;
    }
}
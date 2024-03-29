/*
 * ListUomCategoryView
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

import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.detail.config.EditUomCategoryView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListUomCategoryView extends ANonSearchListEntityView<UomCategory> {
    private static final long serialVersionUID = 959503437038525184L;

    public ListUomCategoryView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("active", ListRendererType.BOOLEAN);
    }

    @Override
    protected Class<? extends AbstractEditView<UomCategory>> getEditViewClass() {
        return EditUomCategoryView.class;
    }

    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.UOM_CATEGORY;
    }

}

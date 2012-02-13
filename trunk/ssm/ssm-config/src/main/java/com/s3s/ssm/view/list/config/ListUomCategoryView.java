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

import java.util.List;

import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.config.EditUomCategoryView;

public class ListUomCategoryView extends AbstractListView<UomCategory> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));

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
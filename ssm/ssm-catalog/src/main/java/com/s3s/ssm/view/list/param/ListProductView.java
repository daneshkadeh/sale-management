/*
 * ListProductView
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
package com.s3s.ssm.view.list.param;

import java.util.List;

import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditProductView;

public class ListProductView extends AbstractListView<Product> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("type", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("manufacturer", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("model", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("description", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("mainUom", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<Product>> getEditViewClass() {
        return EditProductView.class;
    }

}

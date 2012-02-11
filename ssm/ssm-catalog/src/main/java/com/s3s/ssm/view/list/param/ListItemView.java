/*
 * ListItemView
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

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditItemView;

public class ListItemView extends AbstractListView<Item> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("product", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("sumUomName", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("baseSellPrice", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("currency", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<Item>> getEditViewClass() {
        return EditItemView.class;
    }

}

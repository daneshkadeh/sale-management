/*
 * ListStoreView
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

import com.s3s.ssm.entity.catalog.Store;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.param.EditStoreView;

public class ListStoreView extends AbstractListView<Store> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("storedAddress", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("importAddress", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("exportAddress", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("managerCode", DetailFieldType.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<Store>> getEditViewClass() {
        return EditStoreView.class;
    }

}

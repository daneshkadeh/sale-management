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

import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.view.detail.param.EditGoodsView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListGoodsView extends AbstractListView<Goods> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("manufacturer", ListRendererType.TEXT);
        listDataModel.addColumn("model", ListRendererType.TEXT);
        listDataModel.addColumn("description", ListRendererType.TEXT);
        listDataModel.addColumn("mainUom", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Goods>> getEditViewClass() {
        return EditGoodsView.class;
    }

}

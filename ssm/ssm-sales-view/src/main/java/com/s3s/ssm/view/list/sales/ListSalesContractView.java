/*
 * ListSalesContractView
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
package com.s3s.ssm.view.list.sales;

import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.view.detail.sales.EditSalesContractView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListSalesContractView extends AbstractListView<SalesContract> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("supplier", ListRendererType.TEXT);
        listDataModel.addColumn("dateContract", ListRendererType.TEXT);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<SalesContract>> getEditViewClass() {
        return EditSalesContractView.class;
    }

}

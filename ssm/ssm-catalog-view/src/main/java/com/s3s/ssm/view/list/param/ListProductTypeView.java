/*
 * ListProductTypeView
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

import com.s3s.ssm.entity.catalog.ProductType;
import com.s3s.ssm.view.detail.param.EditProductTypeView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListProductTypeView extends ANonSearchListEntityView<ProductType> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        // listDataModel.addColumn("id", ListRendererType.TEXT);
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("productFamilyType", ListRendererType.TEXT);

        // TODO: Parent list must base on productFamilyType, and not itself.
        listDataModel.addColumn("parent", ListRendererType.TEXT);
        listDataModel.addColumn("uomCategory", ListRendererType.TEXT);
        listDataModel.addColumn("active", ListRendererType.BOOLEAN);
    }

    @Override
    protected Class<? extends AbstractEditView<ProductType>> getEditViewClass() {
        return EditProductTypeView.class;
    }

}

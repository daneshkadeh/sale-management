/*
 * ListImportProductFormView
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
package com.s3s.ssm.view.list.store;

import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.view.detail.store.EditImportStoreFormView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListImportStoreFormView extends AbstractListView<ImportStoreForm> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ImportStoreForm>> getEditViewClass() {
        return EditImportStoreFormView.class;
    }

}

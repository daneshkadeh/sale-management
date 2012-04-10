/*
 * ListExportStoreFormView
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

import javax.swing.Icon;

import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.view.detail.store.EditExportStoreFormView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListExportStoreFormView extends AbstractListView<ExportStoreForm> {
    private static final long serialVersionUID = 7393197060716188079L;

    public ListExportStoreFormView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.DATE);
        listDataModel.addColumn("status", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.DATE);
        listDataModel.addColumn("store.name", ListRendererType.TEXT);
        listDataModel.addColumn("staff.fullName", ListRendererType.TEXT);
        listDataModel.addColumn("invoice.invoiceNumber", ListRendererType.LINK);
        listDataModel.addColumn("transType.name", ListRendererType.LINK);
        listDataModel.addColumn("transPrice", ListRendererType.TEXT);
        listDataModel.addColumn("isPrinted", ListRendererType.BOOLEAN);
        listDataModel.addColumn("reqQuanTotal", ListRendererType.NUMBER);
        listDataModel.addColumn("realQuanTotal", ListRendererType.NUMBER);
        listDataModel.addColumn("remainQuanTotal", ListRendererType.NUMBER);
    }

    @Override
    protected Class<? extends AbstractEditView<ExportStoreForm>> getEditViewClass() {
        return EditExportStoreFormView.class;
    }
}
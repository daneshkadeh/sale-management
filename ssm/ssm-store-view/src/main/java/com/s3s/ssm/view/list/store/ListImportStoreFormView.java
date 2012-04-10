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

import java.util.Map;

import javax.swing.JOptionPane;

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
        listDataModel.addColumn("createdDate", ListRendererType.DATE);
        listDataModel.addColumn("status", ListRendererType.TEXT);
        listDataModel.addColumn("salesContract", ListRendererType.TEXT);
        listDataModel.addColumn("supplierName", ListRendererType.TEXT);
        listDataModel.addColumn("receiptDate", ListRendererType.DATE);
        listDataModel.addColumn("receiver", ListRendererType.TEXT);
        listDataModel.addColumn("sender", ListRendererType.TEXT);
        listDataModel.addColumn("isProcessed", ListRendererType.BOOLEAN);
        listDataModel.addColumn("qtyTotal", ListRendererType.NUMBER);
        listDataModel.addColumn("amtTotal", ListRendererType.NUMBER);
        listDataModel.addColumn("taxTotal", ListRendererType.NUMBER);
    }

    @Override
    protected Class<? extends AbstractEditView<ImportStoreForm>> getEditViewClass() {
        return EditImportStoreFormView.class;
    }

    @Override
    protected boolean preShowEditView(ImportStoreForm entity, EditActionEnum action, Map<String, Object> detailParams) {
        super.preShowEditView(entity, action, detailParams);
        int answer = JOptionPane.showConfirmDialog(this.getParent(), "Continue to show edit view?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        return answer == JOptionPane.YES_OPTION;
    }

}

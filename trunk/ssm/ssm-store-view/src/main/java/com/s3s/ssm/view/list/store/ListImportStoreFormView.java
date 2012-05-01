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

import javax.swing.Icon;

import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.detail.store.EditImportStoreFormView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListImportStoreFormView extends AListEntityView<ImportStoreForm> {
    private static final long serialVersionUID = 4982188114504157451L;

    public ListImportStoreFormView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

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
        listDataModel.addColumn("qtyTotal", ListRendererType.NUMBER).summarized().width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("taxTotal", ListRendererType.NUMBER).summarized().width(UIConstants.AMT_COLUMN_WIDTH);
        listDataModel.addColumn("amtTotal", ListRendererType.NUMBER).summarized().width(UIConstants.AMT_COLUMN_WIDTH);
    }

    @Override
    protected Class<? extends AbstractEditView<ImportStoreForm>> getEditViewClass() {
        return EditImportStoreFormView.class;
    }
}

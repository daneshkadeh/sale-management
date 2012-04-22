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

import com.s3s.ssm.entity.store.InventoryStoreForm;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.detail.store.EditInventoryStoreFormView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInventoryStoreFormView extends AbstractListView<InventoryStoreForm> {
    private static final long serialVersionUID = 812018268092313237L;

    public ListInventoryStoreFormView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.DATE);
        listDataModel.addColumn("store.code", ListRendererType.TEXT);
        listDataModel.addColumn("store.name", ListRendererType.TEXT);
        listDataModel.addColumn("notes", ListRendererType.TEXT);
        listDataModel.addColumn("curQtyTotal", ListRendererType.NUMBER).summarized()
                .width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("realQtyTotal", ListRendererType.NUMBER).summarized()
                .width(UIConstants.AMT_COLUMN_WIDTH);
        listDataModel.addColumn("realAmtTotal", ListRendererType.NUMBER).summarized()
                .width(UIConstants.AMT_COLUMN_WIDTH);

    }

    @Override
    protected Class<? extends AbstractEditView<InventoryStoreForm>> getEditViewClass() {
        return EditInventoryStoreFormView.class;
    }
}

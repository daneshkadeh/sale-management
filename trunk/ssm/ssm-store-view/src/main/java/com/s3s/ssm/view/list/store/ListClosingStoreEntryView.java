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

import com.s3s.ssm.entity.store.ClosingStoreEntry;
import com.s3s.ssm.view.detail.store.EditClosingStoreEntryView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListClosingStoreEntryView extends AbstractListView<ClosingStoreEntry> {
    private static final long serialVersionUID = 812018268092313237L;

    public ListClosingStoreEntryView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("closingDate", ListRendererType.DATE);
        listDataModel.addColumn("store", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<ClosingStoreEntry>> getEditViewClass() {
        return EditClosingStoreEntryView.class;
    }
}

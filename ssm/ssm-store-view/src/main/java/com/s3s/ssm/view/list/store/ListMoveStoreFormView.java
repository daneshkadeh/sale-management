/*
 * ListExchangeStoreFormView
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

import com.s3s.ssm.entity.store.MoveStoreForm;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.detail.store.EditMoveStoreFormView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListMoveStoreFormView extends AListEntityView<MoveStoreForm> {
    private static final long serialVersionUID = -4434930101205700627L;

    public ListMoveStoreFormView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.DATE);
        listDataModel.addColumn("receivedDate", ListRendererType.DATE);
        listDataModel.addColumn("sentDate", ListRendererType.DATE);
        listDataModel.addColumn("transType", ListRendererType.TEXT);
        listDataModel.addColumn("exportQtyTotal", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH)
                .summarized();
        listDataModel.addColumn("importQtyTotal", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH)
                .summarized();
    }

    @Override
    protected Class<? extends AbstractEditView<MoveStoreForm>> getEditViewClass() {
        return EditMoveStoreFormView.class;
    }

}

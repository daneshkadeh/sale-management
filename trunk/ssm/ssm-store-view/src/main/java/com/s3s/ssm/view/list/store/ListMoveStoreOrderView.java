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

import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.view.detail.store.EditMoveStoreOrderView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListMoveStoreOrderView extends AListEntityView<MoveStoreOrder> {
    private static final long serialVersionUID = 6005266949770871881L;

    public ListMoveStoreOrderView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.TEXT);
        listDataModel.addColumn("fromDate", ListRendererType.DATE);
        listDataModel.addColumn("destDate", ListRendererType.DATE);
        listDataModel.addColumn("fromStore", ListRendererType.DATE);
        listDataModel.addColumn("destStore", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<MoveStoreOrder>> getEditViewClass() {
        return EditMoveStoreOrderView.class;
    }

}

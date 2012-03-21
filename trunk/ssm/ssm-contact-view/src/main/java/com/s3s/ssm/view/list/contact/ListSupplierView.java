/*
 * ListSupplierView
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
package com.s3s.ssm.view.list.contact;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.view.detail.contact.EditMultiSupplierViewTest;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListSupplierView extends AbstractListView<Supplier> {
    private static final long serialVersionUID = -1414670444682843015L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("name", ListColumnType.TEXT);
        // listDataModel.addColumn("title", FieldTypeEnum.TEXTBOX));
        listDataModel.addColumn("representer", ListColumnType.TEXT);
        listDataModel.addColumn("sex", ListColumnType.BOOLEAN);
        listDataModel.addColumn("position", ListColumnType.TEXT);
        listDataModel.addColumn("phone", ListColumnType.TEXT);
        listDataModel.addColumn("email", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Supplier>> getEditViewClass() {
        return EditMultiSupplierViewTest.class;
    }

}

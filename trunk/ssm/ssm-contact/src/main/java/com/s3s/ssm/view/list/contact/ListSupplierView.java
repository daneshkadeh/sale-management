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

import java.util.List;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.view.detail.contact.EditMultiSupplierViewTest;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

public class ListSupplierView extends AbstractListView<Supplier> {
    private static final long serialVersionUID = -1414670444682843015L;

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
        // listDataModel.add(new DetailAttribute("title", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("representer", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("sex", DetailFieldType.CHECKBOX));
        listDataModel.add(new DetailAttribute("position", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("phone", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("email", DetailFieldType.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<Supplier>> getEditViewClass() {
        return EditMultiSupplierViewTest.class;
    }

}

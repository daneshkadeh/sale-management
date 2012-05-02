/*
 * ListUnitOfMeasureView
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
package com.s3s.ssm.view.list.config;

import javax.swing.Icon;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.detail.config.EditUnitOfMeasureView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListUnitOfMeasureView extends ANonSearchListEntityView<UnitOfMeasure> {
    private static final long serialVersionUID = -2332778174386103471L;

    public ListUnitOfMeasureView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("exchangeRate", ListRendererType.NUMBER);
        listDataModel.addColumn("uomCategory", ListRendererType.TEXT);
        listDataModel.addColumn("isBaseMeasure", ListRendererType.BOOLEAN);
    }

    @Override
    protected Class<? extends AbstractEditView<UnitOfMeasure>> getEditViewClass() {
        return EditUnitOfMeasureView.class;
    }

    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.UOM;
    }
}

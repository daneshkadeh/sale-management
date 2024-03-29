/*
 * ListManufacturerView
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
package com.s3s.ssm.view.list.param;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.view.detail.param.EditManufacturerView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListManufacturerView extends ANonSearchListEntityView<Manufacturer> {
    private static final long serialVersionUID = 82888878072704794L;

    public ListManufacturerView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Manufacturer>> getEditViewClass() {
        return EditManufacturerView.class;
    }

}

/*
 * ListStallView
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

package com.s3s.ssm.view.list.operator;

import javax.swing.Icon;

import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.view.detail.operator.EditStallView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListStallView extends ANonSearchListEntityView<Stall> {

    /**
     * 
     */
    private static final long serialVersionUID = -4623351888966805171L;

    public ListStallView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("manager", ListRendererType.TEXT);
        listDataModel.addColumn("active", ListRendererType.BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Stall>> getEditViewClass() {
        return EditStallView.class;
    }

}

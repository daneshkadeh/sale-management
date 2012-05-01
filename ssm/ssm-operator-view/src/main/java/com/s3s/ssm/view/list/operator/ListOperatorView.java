/*
 * ListOperatorView
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

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.view.detail.operator.EditOperatorView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListOperatorView extends AbstractListView<Operator> {
    private static final long serialVersionUID = 5896113196067552283L;

    public ListOperatorView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("username", ListRendererType.TEXT);
        listDataModel.addColumn("fullName", ListRendererType.TEXT);
        listDataModel.addColumn("isEnabled", ListRendererType.BOOLEAN);
        listDataModel.addColumn("address", ListRendererType.TEXT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Operator>> getEditViewClass() {
        return EditOperatorView.class;
    }

}

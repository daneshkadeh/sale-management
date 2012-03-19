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

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.view.detail.operator.EditOperatorView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListOperatorView extends AbstractListView<Operator> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("username", ListColumnType.TEXT);
        // listDataModel.addColumn("fullname", FieldTypeEnum.TEXTBOX));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Operator>> getEditViewClass() {
        return EditOperatorView.class;
    }

}

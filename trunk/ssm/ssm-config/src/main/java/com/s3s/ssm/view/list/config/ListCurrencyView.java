/*
 * ListCurrencyView
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

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.detail.config.EditCurrencyView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListCurrencyView extends AbstractListView<SCurrency> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("name", ListColumnType.TEXT);
        listDataModel.addColumn("symbol", ListColumnType.TEXT);
        listDataModel.addColumn("isActive", ListColumnType.BOOLEAN);

    }

    @Override
    protected Class<? extends AbstractEditView<SCurrency>> getEditViewClass() {
        return EditCurrencyView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.CURRENCY;
    }

}

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

import java.util.List;

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.config.EditCurrencyView;

public class ListCurrencyView extends AbstractListView<SCurrency> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("symbol", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isActive", FieldTypeEnum.DROPDOWN));

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

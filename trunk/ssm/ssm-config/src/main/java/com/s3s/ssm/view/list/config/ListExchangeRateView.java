/*
 * ListExchangeRateView
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

import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.config.EditExchangeRateView;

public class ListExchangeRateView extends AbstractListView<ExchangeRate> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("updateDate", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("rate", FieldTypeEnum.DROPDOWN));

    }

    @Override
    protected Class<? extends AbstractEditView<ExchangeRate>> getEditViewClass() {
        return EditExchangeRateView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.EXCHANGE_RATE;
    }

}

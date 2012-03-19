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
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.detail.config.EditExchangeRateView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

public class ListExchangeRateView extends AbstractListView<ExchangeRate> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("updateDate", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("rate", DetailFieldType.DROPDOWN));

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

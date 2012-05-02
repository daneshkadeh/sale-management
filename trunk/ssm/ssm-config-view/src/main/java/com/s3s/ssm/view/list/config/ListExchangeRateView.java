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

import javax.swing.Icon;

import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.detail.config.EditExchangeRateView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListExchangeRateView extends ANonSearchListEntityView<ExchangeRate> {
    private static final long serialVersionUID = 6675261522617712910L;

    public ListExchangeRateView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("updateDate", ListRendererType.TEXT);
        listDataModel.addColumn("currency.name", ListRendererType.TEXT);
        listDataModel.addColumn("rate", ListRendererType.NUMBER);
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

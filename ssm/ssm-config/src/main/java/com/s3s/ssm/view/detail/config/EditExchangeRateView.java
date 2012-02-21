/*
 * EditExchangeRateView
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
package com.s3s.ssm.view.detail.config;

import java.util.Map;

import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditExchangeRateView extends AbstractSingleEditView<ExchangeRate> {
    private static final long serialVersionUID = 1L;
    private static final String CURRENCY_REF_ID = "1";

    public EditExchangeRateView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ExchangeRate entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("updateDate", FieldTypeEnum.DATE).mandatory(true);
        detailDataModel.addAttribute("currency", FieldTypeEnum.DROPDOWN).referenceDataId(CURRENCY_REF_ID)
                .mandatory(true);
        detailDataModel.addAttribute("rate", FieldTypeEnum.TEXTBOX).mandatory(true);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ExchangeRate entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(CURRENCY_REF_ID, getDaoHelper().getDao(SCurrency.class).findAll(), null);
    }
}

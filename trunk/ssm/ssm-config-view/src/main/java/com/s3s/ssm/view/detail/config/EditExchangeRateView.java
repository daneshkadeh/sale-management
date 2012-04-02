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
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditExchangeRateView extends AbstractSingleEditView<ExchangeRate> {
    private static final long serialVersionUID = 1L;

    public EditExchangeRateView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ExchangeRate entity) {
        detailDataModel.addAttribute("updateDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("currency", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_CURRENCY)
                .mandatory(true);
        detailDataModel.addAttribute("rate", DetailFieldType.TEXTBOX).mandatory(true);
    }
}

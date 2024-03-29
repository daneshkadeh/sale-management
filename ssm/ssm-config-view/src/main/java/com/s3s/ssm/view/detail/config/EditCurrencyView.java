/*
 * EditCurrencyView
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

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditCurrencyView extends AbstractSingleEditView<SCurrency> {
    private static final long serialVersionUID = 1L;

    public EditCurrencyView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, SCurrency entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("active", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("symbol", DetailFieldType.TEXTBOX).mandatory(true);
    }
}

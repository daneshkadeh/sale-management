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

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

public class EditCurrencyView extends AbstractDetailView<SCurrency> {
    private static final long serialVersionUID = 1L;
    private static final String BOOL_REF_ID = "1";

    public EditCurrencyView(SCurrency entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, SCurrency entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("symbol", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("isActive", FieldTypeEnum.CHECKBOX);
    }
}

/*
 * EditBankView
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

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditBankView extends AbstractSingleEditView<Bank> {
    private static final long serialVersionUID = 728867266827208141L;

    public EditBankView(Bank entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Bank entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTAREA);
    }

}

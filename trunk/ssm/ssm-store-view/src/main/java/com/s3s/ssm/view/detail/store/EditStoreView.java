/*
 * EditStoreView
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
package com.s3s.ssm.view.detail.store;

import java.util.Map;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditStoreView extends AbstractSingleEditView<Store> {

    private static final String REF_OPERATOR = "0";

    public EditStoreView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Store entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("manager", DetailFieldType.DROPDOWN).referenceDataId(REF_OPERATOR);
        detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA).mandatory(true);
        detailDataModel.addAttribute("storedAddress", DetailFieldType.TEXTAREA).mandatory(true);
        detailDataModel.addAttribute("importAddress", DetailFieldType.TEXTAREA).mandatory(true);
        detailDataModel.addAttribute("exportAddress", DetailFieldType.TEXTAREA).mandatory(true);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Store entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_OPERATOR, getDaoHelper().getDao(Operator.class).findAll(), null);
    }
}
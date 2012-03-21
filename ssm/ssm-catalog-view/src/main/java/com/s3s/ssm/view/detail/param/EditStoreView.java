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
package com.s3s.ssm.view.detail.param;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.catalog.Store;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditStoreView extends AbstractSingleEditView<Store> {

    private static final String REF_MANAGER_CODE = "REF_MANAGER_CODE";

    public EditStoreView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Store entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("storedAddress", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("importAddress", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("exportAddress", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("managerCode", DetailFieldType.DROPDOWN).referenceDataId(REF_MANAGER_CODE);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Store entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_MANAGER_CODE, getOperatorCode(), null);
    }

    private List<String> getOperatorCode() {
        // TODO Get list operatorCode from table operator
        return Arrays.asList("Operator1", "Operator2", "Operator3");
    }

}

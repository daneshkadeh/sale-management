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

import com.s3s.ssm.entity.catalog.Store;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

public class EditStoreView extends AbstractDetailView<Store> {

    private static final String REF_MANAGER_CODE = "REF_MANAGER_CODE";

    public EditStoreView(Store entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Store entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("storedAddress", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("importAddress", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("exportAddress", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("managerCode", FieldTypeEnum.DROPDOWN).referenceDataId(REF_MANAGER_CODE);
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

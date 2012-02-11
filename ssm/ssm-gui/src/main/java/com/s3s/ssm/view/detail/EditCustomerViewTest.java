/*
 * EditCustomerViewTest
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
package com.s3s.ssm.view.detail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.CustomerTest;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditCustomerViewTest extends AbstractSingleEditView<CustomerTest> {
    private static final long serialVersionUID = 2202186074068854009L;
    private static final String REF_TEST_MODEL = "0";

    public EditCustomerViewTest(CustomerTest entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, CustomerTest customer) {
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        // DetailDataModel testModel = new DetailDataModel("testField",
        // FieldTypeEnum.MULTI_SELECT_BOX);
        // testModel.setReferenceDataId(REF_TEST_MODEL);
        // listDataModel.add(testModel);
    }

    @Override
    protected void saveOrUpdate(CustomerTest entity) {
        getDaoHelper().getDao(CustomerTest.class).saveOrUpdate(entity);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, CustomerTest entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<String> values = Arrays.asList("Role1", "Role2", "Role3", "Role4", "Role5", "Role6");
        Map<String, String> map = new HashMap<>();
        for (String val : values) {
            map.put(val, val);
        }
        refDataModel.putRefDataList(REF_TEST_MODEL, refDataModel.new ReferenceData<>(map));
    }
}

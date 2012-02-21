/*
 * EditRoleView
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
package com.s3s.ssm.view.detail.security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditRoleView extends AbstractSingleEditView<Role> {
    private static final long serialVersionUID = 1L;
    private static final String BOOL_REF_ID = "1";

    public EditRoleView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Role entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        // detailDataModel.addAttribute("name", FieldTypeEnum.CHECKBOX);
        detailDataModel.addAttribute("isEnable", FieldTypeEnum.DROPDOWN).referenceDataId(BOOL_REF_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Role entity) {
        List<Boolean> boolList = Arrays.asList(Boolean.TRUE, Boolean.FALSE);
        refDataModel.putRefDataList(BOOL_REF_ID,
                refDataModel.new ReferenceData(boolList, new DefaultListCellRenderer()));
    }

}

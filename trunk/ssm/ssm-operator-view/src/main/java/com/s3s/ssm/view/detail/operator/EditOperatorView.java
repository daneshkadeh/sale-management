/*
 * EditOperatorView
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

package com.s3s.ssm.view.detail.operator;

import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditOperatorView extends AbstractSingleEditView<Operator> {

    private static final long serialVersionUID = 7993193127734493726L;
    private static final String ROLE_REF_ID = "1";

    /**
     * @param entity
     */
    public EditOperatorView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Operator entity, Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("username", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("password", DetailFieldType.PASSWORD);
        detailDataModel.addAttribute("fullName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA);
        detailDataModel.addAttribute("roles", DetailFieldType.MULTI_SELECT_LIST_BOX).referenceDataId(ROLE_REF_ID);
        detailDataModel.addAttribute("isEnabled", DetailFieldType.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Operator entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Role> roleList = getDaoHelper().getDao(Role.class).findAll();
        refDataModel.putRefDataList(ROLE_REF_ID,
                refDataModel.new ReferenceData(roleList, new DefaultListCellRenderer()));
    }

}

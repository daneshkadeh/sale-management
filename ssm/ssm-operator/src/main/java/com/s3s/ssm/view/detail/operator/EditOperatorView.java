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

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditOperatorView extends AbstractDetailView<Operator> {

    private static final long serialVersionUID = 7993193127734493726L;
    private static final String ROLE_REF_ID = "1";

    /**
     * @param entity
     */
    public EditOperatorView(Operator entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Operator entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("username", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("password", FieldTypeEnum.PASSWORD);
        detailDataModel.addAttribute("fullName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("phone", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTAREA);
        detailDataModel.addAttribute("roles", FieldTypeEnum.MULTI_SELECT_BOX).referenceDataId(ROLE_REF_ID);
        detailDataModel.addAttribute("isEnabled", FieldTypeEnum.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Operator entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Role> roleList = getDaoHelper().getDao(Role.class).findAll();
        refDataModel.putRefDataList(ROLE_REF_ID,
                refDataModel.new ReferenceData(roleList, new DefaultListCellRenderer()));
    }

}

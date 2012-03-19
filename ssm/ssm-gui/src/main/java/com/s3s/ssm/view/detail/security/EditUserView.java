/*
 * EditUserView
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

import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditUserView extends AbstractSingleEditView<User> {
    private static final long serialVersionUID = 1L;
    private static final String ROLE_REF_ID = "1";

    public EditUserView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, User entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("username", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("password", DetailFieldType.PASSWORD).mandatory(true);
        detailDataModel.addAttribute("roles", DetailFieldType.MULTI_SELECT_LIST_BOX).referenceDataId(ROLE_REF_ID);
        detailDataModel.addAttribute("isEnabled", DetailFieldType.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, User entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<Role> roleList = getDaoHelper().getDao(Role.class).findAll();
        refDataModel.putRefDataList(ROLE_REF_ID,
                refDataModel.new ReferenceData(roleList, new DefaultListCellRenderer()));
    }
}

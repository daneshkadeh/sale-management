/*
 * EditBasicInformationView
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

import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.config.Institution;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditInstitutionView extends AbstractSingleEditView<Institution> {
    private static final long serialVersionUID = 1L;

    public EditInstitutionView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Institution entity) {
        // information of company
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("companyName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("agent", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("position", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("logo.data", DetailFieldType.IMAGE);
        detailDataModel.addAttribute("companyAddress", DetailFieldType.TEXTAREA);
        detailDataModel.addAttribute("tel", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("website", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("email", DetailFieldType.TEXTBOX);
    }

    @Override
    protected Institution loadForCreate() {
        Institution info = super.loadForCreate();
        info.setLogo(new UploadFile());
        return info;
    }

    @Override
    protected Institution loadForEdit(List<String> eagerLoadedProperties) {
        Institution info = super.loadForEdit(eagerLoadedProperties);
        if (info.getLogo() == null) {
            info.setLogo(new UploadFile());
        }
        return info;
    }

    @Override
    protected void saveOrUpdate(Institution entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getLogo().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getLogo());
        super.saveOrUpdate(entity);
    }
}

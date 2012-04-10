/*
 * EditUomCategoryView
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

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditUomCategoryView extends AbstractSingleEditView<UomCategory> {
    private static final long serialVersionUID = 1L;
    private static final String CATE_REF_ID = "1";

    public EditUomCategoryView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, UomCategory entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("parentUomCategory", DetailFieldType.DROPDOWN).referenceDataId(CATE_REF_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, UomCategory entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<UomCategory> cateList = getDaoHelper().getDao(UomCategory.class).findAll();
        cateList.remove(entity);
        refDataModel.putRefDataList(CATE_REF_ID,
                refDataModel.new ReferenceData(cateList, new DefaultListCellRenderer()));
    }
}

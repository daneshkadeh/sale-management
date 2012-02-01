/*
 * EditPartnerCategoryView
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

package com.s3s.ssm.view.detail.contact;

import java.util.List;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.contact.PartnerCategory;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditPartnerCategoryView extends AbstractDetailView<PartnerCategory> {
    private static final String PARTNER_CATE_REF_ID = "1";

    /**
     * @param entity
     */
    public EditPartnerCategoryView(PartnerCategory entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, PartnerCategory entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).setMandatory(true);
        detailDataModel.addAttribute("parentCategory", FieldTypeEnum.DROPDOWN).referenceDataId(PARTNER_CATE_REF_ID);
        detailDataModel.addAttribute("isActive", FieldTypeEnum.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, PartnerCategory entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<PartnerCategory> partnerCateList = getDaoHelper().getDao(PartnerCategory.class).findAll();
        partnerCateList.remove(entity);
        refDataModel.putRefDataList(PARTNER_CATE_REF_ID, refDataModel.new ReferenceData(partnerCateList,
                new DefaultListCellRenderer()));
    }
}

/*
 * EditUnitOfMeasureView
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

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractDetailView;

/**
 * 
 * @author Le Thanh Hoang
 * 
 */
public class EditUnitOfMeasureView extends AbstractDetailView<UnitOfMeasure> {

    private static final String CATE_REF_ID = "1";

    public EditUnitOfMeasureView(UnitOfMeasure entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, UnitOfMeasure entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("uomCategory", FieldTypeEnum.DROPDOWN).referenceDataId(CATE_REF_ID);
        detailDataModel.addAttribute("isBaseMeasure", FieldTypeEnum.CHECKBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, UnitOfMeasure entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<UomCategory> cateList = getDaoHelper().getDao(UomCategory.class).findAll();
        refDataModel.putRefDataList(CATE_REF_ID,
                refDataModel.new ReferenceData(cateList, new DefaultListCellRenderer()));
    }

    @Override
    protected void saveOrUpdate(UnitOfMeasure entity) {
        // if the entity is basic, find the current entity is basic and make it not base measure
        if (entity.getIsBaseMeasure()) {
            List<UnitOfMeasure> uomList = getDaoHelper().getDao(UnitOfMeasure.class).findAll();
            uomList.remove(entity);
            for (UnitOfMeasure uom : uomList) {
                if (uom.getIsBaseMeasure() == true && uom.getUomCategory().equals(entity.getUomCategory())) {
                    uom.setIsBaseMeasure(false);
                    getDaoHelper().getDao(UnitOfMeasure.class).saveOrUpdate(uom);
                    break;
                }
            }
        }

        super.saveOrUpdate(entity);
    }
}

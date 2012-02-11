/*
 * EditManufacturerView
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

import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditManufacturerView extends AbstractSingleEditView<Manufacturer> {
    private static final long serialVersionUID = 1L;

    public EditManufacturerView(Manufacturer entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Manufacturer entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("symbol.data", FieldTypeEnum.IMAGE);
    }

    @Override
    protected void loadForCreate(Manufacturer entity) {
        super.loadForCreate(entity);
        entity.setSymbol(new UploadFile());
    }

    @Override
    protected void loadForEdit(Manufacturer entity) {
        super.loadForEdit(entity);
        if (entity.getSymbol() == null) {
            entity.setSymbol(new UploadFile());
        }
    }

    @Override
    protected void saveOrUpdate(Manufacturer entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getSymbol().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getSymbol());
        super.saveOrUpdate(entity);
    }
}

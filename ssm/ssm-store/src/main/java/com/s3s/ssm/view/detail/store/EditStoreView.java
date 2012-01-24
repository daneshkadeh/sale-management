package com.s3s.ssm.view.detail.store;

import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractDetailView;

/*
 * EditStoreView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

/**
 * @author Chanhchua
 * 
 */
public class EditStoreView extends AbstractDetailView<Store> {

    /**
     * @param entity
     */
    public EditStoreView(Store entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Store entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("storedAddress", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("importAddress", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("exportAddress", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("isEnabled", FieldTypeEnum.CHECKBOX);
    }

}

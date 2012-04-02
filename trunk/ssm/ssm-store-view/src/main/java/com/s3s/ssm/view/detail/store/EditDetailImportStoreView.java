/*
 * EditStoreView
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
package com.s3s.ssm.view.detail.store;

import java.util.Map;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditDetailImportStoreView extends AbstractSingleEditView<DetailImportStore> {

    public EditDetailImportStoreView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, DetailImportStore entity) {
        detailDataModel.addAttribute("item.product.code", DetailFieldType.LABEL);
        detailDataModel.addAttribute("item.product.name", DetailFieldType.LABEL);
        detailDataModel.addAttribute("quantity", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("priceUnit", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("priceSubtotal", DetailFieldType.TEXTBOX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DetailImportStore loadForCreate() {
        ImportStoreForm importStore = new ImportStoreForm();
        Item item = new Item();
        DetailImportStore detail = super.loadForCreate();
        detail.setImportStoreForm(importStore);
        detail.setItem(item);
        return detail;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, DetailImportStore entity) {
        super.setReferenceDataModel(refDataModel, entity);
    }
}
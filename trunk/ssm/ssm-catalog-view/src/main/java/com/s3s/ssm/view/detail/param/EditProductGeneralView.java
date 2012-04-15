/*
 * EditProductView
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

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.ProductType;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.i18n.ControlConstants;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public abstract class EditProductGeneralView<T extends Product> extends AbstractSingleEditView<T> {
    private static final long serialVersionUID = 4375985070956587330L;
    private static final String TYPE_REF_ID = "1";

    public EditProductGeneralView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, T entity, Map<String, Object> request) {
        detailDataModel.tab(ControlConfigUtils.getString(ControlConstants.MESSAGE_KEY_GENERAL), "General info", null);
        addTabGeneral(detailDataModel);

        detailDataModel.tab(ControlConfigUtils.getString("tab.EditProductGeneralView.MoreInfo"), "More info", null);
        detailDataModel.addAttribute("uploadFile.data", DetailFieldType.IMAGE);
    }

    protected void addTabGeneral(DetailDataModel detailDataModel) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(TYPE_REF_ID);
        detailDataModel.addAttribute("vatRate", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("description", DetailFieldType.TEXTAREA).editable(true);
    }

    protected void setReferenceDataModel(com.s3s.ssm.model.ReferenceDataModel refDataModel, T entity) {
        super.setReferenceDataModel(refDataModel, entity);
        DetachedCriteria dc = getDaoHelper().getDao(ProductType.class).getCriteria();
        dc.add(Restrictions.eq("productFamilyType", getProductFamilyType()));
        refDataModel.putRefDataList(TYPE_REF_ID, getDaoHelper().getDao(ProductType.class).findByCriteria(dc), null);

    };

    protected abstract ProductFamilyType getProductFamilyType();

    @Override
    protected T loadForCreate(Map<String, Object> request) {
        T product = super.loadForCreate(request);
        product.setUploadFile(new UploadFile());
        return product;
    }

    @Override
    protected T loadForEdit(List<String> eagerLoadedProperties) {
        T product = super.loadForEdit(eagerLoadedProperties);
        if (product.getUploadFile() == null) {
            product.setUploadFile(new UploadFile());
        }
        return product;
    }

    @Override
    protected void saveOrUpdate(T entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getUploadFile().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getUploadFile());
        super.saveOrUpdate(entity);
    }
}

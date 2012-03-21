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

import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductType;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditProductGeneralView<T extends Product> extends AbstractSingleEditView<Product> {
    private static final long serialVersionUID = 4375985070956587330L;
    private static final String TYPE_REF_ID = "1";
    private static final String MANU_REF_ID = "2";
    private static final String UOM_REF_ID = "3";
    private static final String REF_PROPERTIES_ID = "4";

    public EditProductGeneralView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Product entity) {
        detailDataModel.tab("General", "General info", null);
        addTabGeneral(detailDataModel);

        detailDataModel.tab("More info", "More info", null);
        detailDataModel.addAttribute("uploadFile.data", DetailFieldType.IMAGE);
    }

    protected void addTabGeneral(DetailDataModel detailDataModel) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(TYPE_REF_ID);
        detailDataModel.addAttribute("manufacturer", DetailFieldType.DROPDOWN).referenceDataId(MANU_REF_ID);
        detailDataModel.addAttribute("model", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("description", DetailFieldType.TEXTAREA).editable(true);
        detailDataModel.addAttribute("mainUom", DetailFieldType.DROPDOWN).referenceDataId(UOM_REF_ID);
        detailDataModel.addAttribute("properties", DetailFieldType.MULTI_SELECT_LIST_BOX)
                .referenceDataId(REF_PROPERTIES_ID).width(300);
        System.err.println(entity.getProperties());
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Product entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(TYPE_REF_ID, getDaoHelper().getDao(ProductType.class).findAll(), null);
        refDataModel.putRefDataList(MANU_REF_ID, getDaoHelper().getDao(Manufacturer.class).findAll(), null);
        refDataModel.putRefDataList(UOM_REF_ID, getDaoHelper().getDao(UnitOfMeasure.class).findAll(), null);
        refDataModel.putRefDataList(REF_PROPERTIES_ID, getDaoHelper().getDao(ProductProperty.class).findAll(), null);
    }

    @Override
    protected Product loadForCreate() {
        Product product = super.loadForCreate();
        product.setUploadFile(new UploadFile());
        return product;
    }

    @Override
    protected Product loadForEdit(List<String> eagerLoadedProperties) {
        eagerLoadedProperties.add("properties");
        Product product = super.loadForEdit(eagerLoadedProperties);
        if (product.getUploadFile() == null) {
            product.setUploadFile(new UploadFile());
        }
        return product;
    }

    @Override
    protected void saveOrUpdate(Product entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getUploadFile().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getUploadFile());
        super.saveOrUpdate(entity);
    }

}

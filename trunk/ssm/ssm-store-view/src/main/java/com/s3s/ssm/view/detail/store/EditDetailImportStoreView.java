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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.component.IValueChangedListener;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.util.StoreHelper;

public class EditDetailImportStoreView extends AbstractSingleEditView<DetailImportStore> {
    private static String REF_ITEM_LIST = "0";
    private static String REF_BASE_UOM_LIST = "0";

    public EditDetailImportStoreView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, DetailImportStore entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("lineNo", DetailFieldType.LABEL);
        detailDataModel.addAttribute("product", DetailFieldType.ENTITY_CHOOSER).cacheDataId(CacheId.REF_LIST_PRODUCT);
        detailDataModel.addAttribute("item", DetailFieldType.DROPDOWN).referenceDataId(REF_ITEM_LIST);
        detailDataModel.addAttribute("uom", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_UNIT_UOM);
        detailDataModel.addAttribute("baseUom", DetailFieldType.DROPDOWN).referenceDataId(REF_BASE_UOM_LIST)
                .enable(false);
        detailDataModel.addAttribute("quantity", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("priceUnit", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("priceSubtotal", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DetailImportStore loadForCreate(Map<String, Object> request) {
        // TODO:Hoang should get from ContextProvider
        UnitOfMeasure baseUom = serviceProvider.getService(IConfigService.class).getBaseUnitUom();
        // TODO:Hoang ahould get parent form
        ImportStoreForm importStore = new ImportStoreForm();
        DetailImportStore detail = super.loadForCreate(request);
        // TODO: Hoang should have
        detail.setLineNo(1);
        detail.setImportStoreForm(importStore);
        detail.setBaseUom(baseUom);
        return detail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, DetailImportStore entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JTextField tfdQuantity = (JTextField) name2AttributeComponent.get("quantity").getComponent();
        final MoneyComponent mPriceUnit = (MoneyComponent) name2AttributeComponent.get("priceUnit").getComponent();
        final MoneyComponent mSubtotal = (MoneyComponent) name2AttributeComponent.get("priceSubtotal").getComponent();
        tfdQuantity.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                mSubtotal.setMoney(StoreHelper.calculatePriceSubtotal(tfdQuantity.getText(), mPriceUnit.getMoney()));
            }

            @Override
            public void focusGained(FocusEvent e) {
            }
        });
        mPriceUnit.addValueChangedListener(new IValueChangedListener() {

            @Override
            public void doValueChanged(ChangeEvent e) {
                mSubtotal.setMoney(StoreHelper.calculatePriceSubtotal(tfdQuantity.getText(), mPriceUnit.getMoney()));
            }
        });

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, DetailImportStore entity) {
        super.setReferenceDataModel(refDataModel, entity);
        // TODO: Hoang handle after user selects product
        refDataModel.putRefDataList(REF_ITEM_LIST, getDaoHelper().getDao(Item.class).findAll(), null);
        // TODO:Hoang should get from ContextProvider
        UnitOfMeasure baseUom = serviceProvider.getService(IConfigService.class).getBaseUnitUom();
        refDataModel.putRefDataList(REF_BASE_UOM_LIST, Arrays.asList(baseUom), null);
    }
}
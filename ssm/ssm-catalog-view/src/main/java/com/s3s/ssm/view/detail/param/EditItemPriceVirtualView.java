/*
 * EditItemPriceVirtualView
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

import java.util.Map;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.helper.CatalogHelper;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditItemPriceVirtualView extends AbstractSingleEditView<ItemPrice> {
    private static final String REF_AUDIENCE_CATE = "REF_AUDIENCE_CATE";
    private static final String REF_CURRENCY_ID = "REF_CURRENCY_ID";

    public EditItemPriceVirtualView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ItemPrice entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("audienceCategory", DetailFieldType.DROPDOWN).referenceDataId(REF_AUDIENCE_CATE);
        detailDataModel.addAttribute("sellPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
    }

    @Override
    protected ItemPrice loadForCreate() {
        ItemPrice itemPrice = super.loadForCreate();
        itemPrice.setItem((Item) this.getParentObject());
        return itemPrice;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ItemPrice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_AUDIENCE_CATE, getDaoHelper().getDao(AudienceCategory.class).findAll(), null);
        refDataModel.putRefDataList(REF_CURRENCY_ID, CatalogHelper.getCurrenciesCode(getDaoHelper()), null);
    }

    // @Override
    // protected void saveOrUpdate(ItemPrice entity) {
    // // Do nothing, wait for saved by master entity Item.
    // }
}

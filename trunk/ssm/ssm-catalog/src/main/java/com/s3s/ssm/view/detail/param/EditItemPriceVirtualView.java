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
import com.s3s.ssm.entity.contact.PartnerCategory;
import com.s3s.ssm.helper.CatalogHelper;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditItemPriceVirtualView extends AbstractSingleEditView<ItemPrice> {
    private static final String REF_CONTACT_TYPE = "REF_CONTACT_TYPE";
    private static final String REF_CURRENCY_ID = "REF_CURRENCY_ID";

    public EditItemPriceVirtualView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ItemPrice entity) {
        detailDataModel.addAttribute("item", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("partnerCategory", DetailFieldType.DROPDOWN).referenceDataId(REF_CONTACT_TYPE);
        detailDataModel.addAttribute("sellPrice", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("currency", DetailFieldType.DROPDOWN).referenceDataId(REF_CURRENCY_ID);
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
        refDataModel.putRefDataList(REF_CONTACT_TYPE, getDaoHelper().getDao(PartnerCategory.class).findAll(), null);
        refDataModel.putRefDataList(REF_CURRENCY_ID, CatalogHelper.getCurrenciesCode(getDaoHelper()), null);
    }

    // @Override
    // protected void saveOrUpdate(ItemPrice entity) {
    // // Do nothing, wait for saved by master entity Item.
    // }
}

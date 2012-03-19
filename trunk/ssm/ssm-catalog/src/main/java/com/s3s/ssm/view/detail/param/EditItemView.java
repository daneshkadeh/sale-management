/*
 * EditItemView
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

import org.eclipse.core.internal.runtime.Product;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.helper.CatalogHelper;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

/**
 * This view is only used to TEST. A list items should be shown on 1 product config. The entity tree view is required
 * for this case.
 * 
 * @author phamcongbang
 * 
 */
public class EditItemView extends AbstractMasterDetailView<Item, ItemPrice> {

    private static final String REF_PRODUCT_ID = "REF_PRODUCT_ID";
    private static final String REF_CURRENCY_ID = "REF_CURRENCY_ID";
    private static final String REF_UOM_ID = "REF_UOM_ID";

    public EditItemView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Item entity) {
        detailDataModel.addAttribute("product", DetailFieldType.DROPDOWN).referenceDataId(REF_PRODUCT_ID);
        detailDataModel.addAttribute("sumUomName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("baseSellPrice", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("currency", DetailFieldType.DROPDOWN).referenceDataId(REF_CURRENCY_ID);
        detailDataModel.addAttribute("listUom", DetailFieldType.MULTI_SELECT_LIST_BOX).referenceDataId(REF_UOM_ID);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Item entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PRODUCT_ID, getDaoHelper().getDao(Product.class).findAll(), null);
        refDataModel.putRefDataList(REF_CURRENCY_ID, CatalogHelper.getCurrenciesCode(getDaoHelper()), null);
        refDataModel.putRefDataList(REF_UOM_ID, getDaoHelper().getDao(UnitOfMeasure.class).findAll(), null);
        // refDataModel.putRefDataList(REF_UOM_ID, Arrays.asList("0", "1", "2"), null);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("partnerCategory", ListColumnType.TEXT);
        listDataModel.addColumn("sellPrice", ListColumnType.TEXT);
        listDataModel.addColumn("currency", ListColumnType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<ItemPrice>> getChildDetailViewClass() {
        return EditItemPriceVirtualView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "listItemPrices";
    }

    @Override
    protected void addDetailIntoMaster(Item masterEntity, ItemPrice detailEntity) {
        masterEntity.addItemPrice(detailEntity);
    }

}

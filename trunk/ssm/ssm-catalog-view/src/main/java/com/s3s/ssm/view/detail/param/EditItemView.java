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

import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.DaoHelperImpl;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * This view is only used to TEST. A list items should be sho wn on 1 product config. The entity tree view is required
 * for this case.
 * 
 * @author phamcongbang
 * 
 */
public class EditItemView extends AbstractMasterDetailView<Item, ItemPrice> {

    private static final String REF_PRODUCT_ID = "REF_PRODUCT_ID";
    private static final String REF_UOM_ID = "REF_UOM_ID";

    public EditItemView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, Item entity, Map<String, Object> request) {
        if (request.get(PARAM_PARENT_ID) != null) {
            if (entity.getProduct() == null) {
                entity.setProduct(daoHelper.getDao(Product.class).findById(
                        Long.valueOf(request.get(PARAM_PARENT_ID).toString())));
            }
        } else {
            detailDataModel.addAttribute("product", DetailFieldType.DROPDOWN).referenceDataId(REF_PRODUCT_ID);
        }

        detailDataModel.addAttribute("sumUomName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("originPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("baseSellPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);

        if (isGoodProduct(entity)) {
            Goods good = DaoHelperImpl.downCast(Goods.class, entity.getProduct());
            for (ProductProperty property : good.getProperties()) {
                ProductPropertyElement selectedElement = entity.getPropertyValue(property) != null ? entity
                        .getPropertyValue(property).getElement() : null;
                detailDataModel.addRawAttribute("FIELD_" + property.getId(), DetailFieldType.DROPDOWN)
                        .label(property.getName()).referenceDataId("REF_" + property.getId()).value(selectedElement);
            }
        }
    }

    private boolean isGoodProduct(Item entity) {
        return entity.getProduct() != null
                && entity.getProduct().getType().getProductFamilyType() == ProductFamilyType.GOODS;
    }

    @Override
    protected void bindingValue(Item entity, String name, Object value, DetailAttribute detailAttribute) {
        super.bindingValue(entity, name, value, detailAttribute);
        if (isGoodProduct(entity)) {
            Goods good = DaoHelperImpl.downCast(Goods.class, entity.getProduct());
            for (ProductProperty property : good.getProperties()) {
                if (name.equals("FIELD_" + property.getId())) {
                    ItemPropertyValue propertyValue = entity.getPropertyValue(property);
                    if (propertyValue == null) {
                        propertyValue = new ItemPropertyValue();
                        propertyValue.setItem(entity);
                        propertyValue.setProperty(property);
                        entity.getListPropertyValue().add(propertyValue);
                    }
                    propertyValue.setElement((ProductPropertyElement) value);
                }
            }
        }
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Item entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PRODUCT_ID, getDaoHelper().getDao(Product.class).findAll(), null);
        refDataModel.putRefDataList(REF_UOM_ID, getDaoHelper().getDao(UnitOfMeasure.class).findAll(), null);
        // refDataModel.putRefDataList(REF_UOM_ID, Arrays.asList("0", "1", "2"), null);

        // put all references to properties
        if (isGoodProduct(entity)) {
            Goods good = DaoHelperImpl.downCast(Goods.class, entity.getProduct());
            for (ProductProperty property : good.getProperties()) {
                refDataModel.putRefDataList("REF_" + property.getId(), property.getElements());
            }
        }
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("audienceCategory", ListRendererType.TEXT);
        listDataModel.addColumn("sellPrice", ListRendererType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<ItemPrice>> getChildDetailViewClass() {
        return EditItemPriceVirtualView.class;
    }

    @Override
    protected String getParentFieldName() {
        return "item";
    }

    @Override
    protected String getChildFieldName() {
        return "listItemPrices";
    }

}

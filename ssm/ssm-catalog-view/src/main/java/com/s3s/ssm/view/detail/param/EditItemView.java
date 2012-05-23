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
import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.DaoHelperImpl;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

/**
 * This view is only used to TEST. A list items should be shown on 1 product config. The entity tree view is required
 * for this case.
 * 
 * @author phamcongbang
 * 
 */
public class EditItemView extends AbstractSingleEditView<Item> {

    private static final String REF_PRODUCT_ID = "REF_PRODUCT_ID";
    private static final String REF_UOM_ID = "REF_UOM_ID";

    public EditItemView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, Item entity, Map<String, Object> request) {
        if (entity.getProduct() == null) {
            detailDataModel.addAttribute("product", DetailFieldType.DROPDOWN).referenceDataId(REF_PRODUCT_ID);
        }

        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("sumUomName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("originPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("baseSellPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);

        if (isProductWithProperties(entity)) {
            Product good = entity.getProduct();
            for (ProductProperty property : good.getProperties()) {
                ProductPropertyElement selectedElement = entity.getPropertyValue(property) != null ? entity
                        .getPropertyValue(property).getElement() : null;
                detailDataModel.addRawAttribute("FIELD_" + property.getId(), DetailFieldType.DROPDOWN)
                        .label(property.getName()).referenceDataId("REF_" + property.getId()).value(selectedElement);
            }
        }
        detailDataModel.addAttribute("listItemPrices", DetailFieldType.LIST).componentInfo(
                createListItemPriceComponent());
    }

    @Override
    protected Item loadForCreate(Map<String, Object> request) {
        Item entity = super.loadForCreate(request);
        if (request.get(PARAM_PARENT_ID) != null) {
            Product product = daoHelper.getDao(Product.class).findById(
                    Long.valueOf(request.get(PARAM_PARENT_ID).toString()));
            entity.setProduct(product);
            if (product.getType().getProductFamilyType() == ProductFamilyType.GOODS) {
                Goods goods = DaoHelperImpl.downCast(Goods.class, product);
                entity.setCode(goods.getCode());
                entity.setUom(goods.getMainUom());
                entity.setBaseSellPrice(goods.getBaseSellPrice());
                entity.setOriginPrice(goods.getOriginPrice());
            }
        }
        return entity;
    }

    private IComponentInfo createListItemPriceComponent() {
        ListItemPriceComponent component = new ListItemPriceComponent(null, null, null);
        return new ListComponentInfo(component, "item");
    }

    private boolean isProductWithProperties(Item entity) {
        return entity.getProduct() != null
                && (entity.getProduct().getType().getProductFamilyType() == ProductFamilyType.GOODS
                        || entity.getProduct().getType().getProductFamilyType() == ProductFamilyType.SERVICE || entity
                        .getProduct().getType().getProductFamilyType() == ProductFamilyType.VOUCHER);
    }

    @Override
    protected void bindingValue(Item entity, String name, Object value, DetailAttribute detailAttribute) {
        super.bindingValue(entity, name, value, detailAttribute);
        if (isProductWithProperties(entity)) {
            Product good = entity.getProduct();
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
        if (isProductWithProperties(entity)) {
            Product good = entity.getProduct();
            for (ProductProperty property : good.getProperties()) {
                refDataModel.putRefDataList("REF_" + property.getId(), property.getElements());
            }
        }
    }

}

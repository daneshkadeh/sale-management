/*
 * EditImportProductFormView
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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class EditImportStoreFormView extends AbstractMasterDetailView<ImportStoreForm, DetailImportStore> {
    // TODO:Hoang remove bellow after ListDataModel support caching
    private static String REF_UNIT_UOM = "1";
    private static String REF_LIST_PRODUCT = "2";
    private static String REF_LIST_ITEM = "3";

    /**
     * @param entity
     */
    public EditImportStoreFormView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        // TODO: Hoang must set max, min for column
        listDataModel.setEditable(true);
        listDataModel.addColumn("lineNo", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_LIST_PRODUCT);
        listDataModel.addColumn("product.name", ListRendererType.TEXT).notEditable();
        // TODO: Hoang the data should be updated after choosing the product
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_LIST_ITEM);
        listDataModel.addColumn("uom", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_UNIT_UOM);
        listDataModel.addColumn("baseUom", ListRendererType.TEXT, ListEditorType.TEXTFIELD).notEditable();
        listDataModel.addColumn("quantity", ListRendererType.NUMBER, ListEditorType.TEXTFIELD);
        listDataModel.addColumn("priceUnit", ListRendererType.NUMBER, ListEditorType.TEXTFIELD);
        listDataModel.addColumn("priceSubtotal", ListRendererType.NUMBER, ListEditorType.TEXTFIELD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<DetailImportStore>> getChildDetailViewClass() {
        return EditDetailImportStoreView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getChildFieldName() {
        return "detailImportStores";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addDetailIntoMaster(ImportStoreForm masterEntity, DetailImportStore detailEntity) {
        masterEntity.addDetailImports(detailEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ImportStoreForm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("printAfterSave", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_IMPORT_STORE_STATUS).newColumn();
        detailDataModel.addAttribute("store", DetailFieldType.ENTITY_CHOOSER).cacheDataId(CacheId.REF_LIST_STORE);
        detailDataModel.addAttribute("mobilizationOrder", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("salesContract", DetailFieldType.ENTITY_CHOOSER).cacheDataId(
                CacheId.REF_LIST_SALES_CONTRACT);
        detailDataModel.addAttribute("importNum", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("supplierName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("shipNum", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("receiptDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("shipPriceType", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_SHIP_PRICE_TYPE).newColumn();
        detailDataModel.addAttribute("receiver", DetailFieldType.ENTITY_CHOOSER).cacheDataId(CacheId.REF_LIST_OPERATOR);
        detailDataModel.addAttribute("shipPrice", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("sender", DetailFieldType.TEXTBOX);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, ImportStoreForm entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JComboBox cbShipPriceType = (JComboBox) name2AttributeComponent.get("shipPriceType").getComponent();
        final JTextField tfdShipPrice = (JTextField) name2AttributeComponent.get("shipPrice").getComponent();

        cbShipPriceType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ShipPriceType type = (ShipPriceType) cbShipPriceType.getSelectedItem();
                ShipPrice shipPrice = serviceProvider.getService(IStoreService.class)
                        .getLatestShipPrice(type.getCode());
                String strShipPrice = shipPrice.getPrice().getValue().toString();
                tfdShipPrice.setText(strShipPrice);
            }
        });
    }

    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ImportStoreForm entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_UNIT_UOM, serviceProvider.getService(IConfigService.class).getUnitUom());
        refDataModel.putRefDataList(REF_LIST_PRODUCT, serviceProvider.getService(ICatalogService.class)
                .getListProducts());
        refDataModel.putRefDataList(REF_LIST_ITEM, serviceProvider.getService(ICatalogService.class).getAllItem());
    }
}
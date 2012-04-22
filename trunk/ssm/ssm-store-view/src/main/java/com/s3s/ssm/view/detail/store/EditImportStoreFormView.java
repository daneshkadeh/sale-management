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

import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.component.MoneyComponent;
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
    private static final String REF_CURRENCY = "REF_CURRENCY";

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
        // listDataModel.setEditable(true);
        // listDataModel.addColumn("lineNo", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.COMBOBOX)
                .referenceDataId(REF_LIST_PRODUCT).width(180);
        listDataModel.addColumn("productName", ListRendererType.TEXT).notEditable().width(290);
        // TODO: Hoang the data should be updated after choosing the product
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_LIST_ITEM)
                .width(205);
        listDataModel.addColumn("uom", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_UNIT_UOM)
                .width(70);
        listDataModel.addColumn("baseUom", ListRendererType.TEXT, ListEditorType.TEXTFIELD).notEditable();
        listDataModel.addColumn("quantity", ListRendererType.NUMBER, ListEditorType.TEXTFIELD).width(70)
                .width(UIConstants.QTY_COLUMN_WIDTH).summarized();
        listDataModel.addColumn("priceUnit", ListRendererType.TEXT, ListEditorType.MONEY)
                .width(UIConstants.AMT_COLUMN_WIDTH).referenceDataId(REF_CURRENCY);
        listDataModel.addColumn("priceSubtotal", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(UIConstants.AMT_COLUMN_WIDTH).summarized();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<DetailImportStore>> getChildDetailViewClass() {
        return EditDetailImportStoreView.class;
    }

    @Override
    protected String getParentFieldName() {
        return "importStoreForm";
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
    protected void initialPresentationView(DetailDataModel detailDataModel, ImportStoreForm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        // detailDataModel.addAttribute("printAfterSave", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_IMPORT_STORE_STATUS).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("shipNum", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("store", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE);
        detailDataModel.addAttribute("shipPriceType", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_SHIP_PRICE_TYPE).newColumn();
        detailDataModel.addAttribute("salesContract", DetailFieldType.DROPDOWN).cacheDataId(
                CacheId.REF_LIST_SALES_CONTRACT);
        detailDataModel.addAttribute("shipPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY)
                .newColumn();
        detailDataModel.addAttribute("supplierName", DetailFieldType.TEXTBOX);

        detailDataModel.addAttribute("receiptDate", DetailFieldType.DATE);

        detailDataModel.addAttribute("receiver", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_OPERATOR);

        detailDataModel.addAttribute("sender", DetailFieldType.TEXTBOX);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, ImportStoreForm entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JComboBox cbShipPriceType = (JComboBox) name2AttributeComponent.get("shipPriceType").getComponent();
        final MoneyComponent mShipPrice = (MoneyComponent) name2AttributeComponent.get("shipPrice").getComponent();

        cbShipPriceType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ShipPriceType type = (ShipPriceType) cbShipPriceType.getSelectedItem();
                ShipPrice shipPrice = serviceProvider.getService(IStoreService.class)
                        .getLatestShipPrice(type.getCode());
                mShipPrice.setMoney(shipPrice.getPrice());
            }
        });
    }

    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ImportStoreForm entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_UNIT_UOM, serviceProvider.getService(IConfigService.class).getUnitUom());
        refDataModel.putRefDataList(REF_LIST_PRODUCT, serviceProvider.getService(ICatalogService.class)
                .getListProducts());
        refDataModel.putRefDataList(REF_LIST_ITEM, serviceProvider.getService(ICatalogService.class).getAllItem());
        refDataModel.putRefDataList(REF_CURRENCY, serviceProvider.getService(IConfigService.class).getCurrencyCodes());
    }

    @Override
    protected String getDefaultTitle(ImportStoreForm entity) {
        return ControlConfigUtils.getString("label.ImportStoreForm.detail.title") + UIConstants.BLANK
                + entity.getCode();
    }
}
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
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class EditImportStoreFormView extends AbstractMasterDetailView<ImportStoreForm, DetailImportStore> {

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
        listDataModel.addColumn("product.code", ListColumnType.TEXT);
        listDataModel.addColumn("product.name", ListColumnType.TEXT);
        listDataModel.addColumn("uom", ListColumnType.TEXT);
        listDataModel.addColumn("baseUom", ListColumnType.TEXT);
        listDataModel.addColumn("quantity", ListColumnType.TEXT);
        listDataModel.addColumn("priceUnit", ListColumnType.TEXT);
        listDataModel.addColumn("priceSubtotal", ListColumnType.TEXT);
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
    public void initialPresentationView(DetailDataModel detailDataModel, ImportStoreForm entity) {
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
}
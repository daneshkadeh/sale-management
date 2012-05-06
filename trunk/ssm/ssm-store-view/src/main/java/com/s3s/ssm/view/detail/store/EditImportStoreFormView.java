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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.util.StoreViewHelper;

public class EditImportStoreFormView extends AbstractSingleEditView<ImportStoreForm> {
    private static final long serialVersionUID = -3925196237801396900L;
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
    protected void initialPresentationView(DetailDataModel detailDataModel, ImportStoreForm entity,
            Map<String, Object> request) {
        String supplierName = "";
        if (entity.getSalesContract() != null) {
            supplierName = entity.getSalesContract().getSupplier().getName();
        }
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        // detailDataModel.addAttribute("printAfterSave", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_IMPORT_STORE_STATUS).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("shipNum", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("store", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE);
        detailDataModel.addAttribute("shipPriceType", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_SHIP_PRICE_TYPE).newColumn();
        detailDataModel.addAttribute("receiver", DetailFieldType.SEARCHER).componentInfo(
                ComponentFactory.createStorekeeperComponentInfo());
        detailDataModel.addAttribute("shipPrice", DetailFieldType.MONEY).editable(false)
                .cacheDataId(CacheId.REF_LIST_CURRENCY).newColumn();
        detailDataModel.addAttribute("salesContract", DetailFieldType.DROPDOWN).cacheDataId(
                CacheId.REF_LIST_SALES_CONTRACT);
        detailDataModel.addAttribute("receiptDate", DetailFieldType.DATE).newColumn();
        detailDataModel.addRawAttribute("supplierName", DetailFieldType.TEXTBOX).value(supplierName).editable(false);
        detailDataModel.addAttribute("sender", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("importationSC", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_EMPTY);
        detailDataModel.addAttribute("detailImportStores", DetailFieldType.LIST).componentInfo(
                createImportDetailsComponentInfo());
    }

    private IComponentInfo createImportDetailsComponentInfo() {
        ListImportDetailComponent component = new ListImportDetailComponent(null, null, null);
        return new ListComponentInfo(component, "importStoreForm");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, ImportStoreForm entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JTextField tfdShipNum = (JTextField) name2AttributeComponent.get("shipNum").getComponent();
        final JComboBox cbShipPriceType = (JComboBox) name2AttributeComponent.get("shipPriceType").getComponent();
        final MoneyComponent mShipPrice = (MoneyComponent) name2AttributeComponent.get("shipPrice").getComponent();
        final JComboBox cbSalesContract = (JComboBox) name2AttributeComponent.get("salesContract").getComponent();
        final JTextField tfdSupName = (JTextField) name2AttributeComponent.get("supplierName").getComponent();
        final JComboBox cbImportationSC = (JComboBox) name2AttributeComponent.get("importationSC").getComponent();
        tfdShipNum.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                StoreViewHelper.updateShipPrice(cbShipPriceType, tfdShipNum, mShipPrice);
            }

            @Override
            public void focusGained(FocusEvent e) {
            }
        });

        cbShipPriceType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                StoreViewHelper.updateShipPrice(cbShipPriceType, tfdShipNum, mShipPrice);
            }
        });

        cbSalesContract.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                SalesContract salesContract = (SalesContract) cbSalesContract.getSelectedItem();
                if (salesContract != null) {
                    Partner supplier = salesContract.getSupplier();
                    if (supplier != null) {
                        tfdSupName.setText(supplier.getName());
                    } else {
                        tfdSupName.setText("");
                    }

                    DefaultComboBoxModel model = new DefaultComboBoxModel<>(salesContract.getListImportation()
                            .toArray());
                    cbImportationSC.setModel(model);
                } else {
                    tfdSupName.setText("");
                    DefaultComboBoxModel model = new DefaultComboBoxModel<>(Collections.EMPTY_LIST.toArray());
                    cbImportationSC.setModel(model);
                }
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void preSaveOrUpdate(ImportStoreForm entity) {
        super.preSaveOrUpdate(entity);
        Money amtTotal = Money.create(CurrencyEnum.VND, 0L);
        Integer qtyTotal = 0;
        Money shipPrice = entity.getShipPrice();
        for (DetailImportStore detail : entity.getDetailImportStores()) {
            Money priceSubtotal = detail.getPriceSubtotal();
            Integer qty = detail.getQuantity();
            amtTotal = amtTotal.plus(priceSubtotal);
            qtyTotal = qtyTotal + qty;
        }
        amtTotal = amtTotal.plus(shipPrice);
        entity.setAmtTotal(amtTotal);
        entity.setQtyTotal(qtyTotal);
    }
}
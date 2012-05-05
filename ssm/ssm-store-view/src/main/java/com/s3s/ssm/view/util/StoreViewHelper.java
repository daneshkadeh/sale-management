/*
 * StoreUtil
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.util;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.commons.lang.math.NumberUtils;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.ClosingStoreEntry;
import com.s3s.ssm.entity.store.DetailClosingStore;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.DetailInventoryStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.entity.store.InventoryStoreForm;
import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.view.ViewHelper;
import com.s3s.ssm.view.component.MoneyComponent;

/**
 * @author Le Thanh Hoang
 * 
 */
// TODO: Should improve performance. Which function is on service or client
public class StoreViewHelper extends ViewHelper {

    public static Money calculatePriceSubtotal(String strQuantity, Money mPriceUnit) {
        int quan = NumberUtils.toInt(strQuantity);
        Money mPriceSubtotal = mPriceUnit.multiply(quan);
        return mPriceSubtotal;
    }

    public static ExportStoreForm initExportStoreForm(ExportStoreForm form, Invoice invoice) {
        form.setInvoice(invoice);
        form.setCustCode(invoice.getContact().getCode());
        form.setCustName(invoice.getContact().getName());
        switch (invoice.getStoreStatus()) {
        case NO_ACTION:
            form.getExportDetails().addAll(initDetailExportStore(invoice));
            break;
        case EXPORTING:
            ExportStoreForm latestForm = serviceProvider.getService(IStoreService.class).getLatestExportStoreForm(
                    invoice.getInvoiceNumber());
            form.getExportDetails().addAll(initDetailExportStore(latestForm));
            break;
        default:
            break;
        }
        return form;
    }

    public static InventoryStoreForm initInventoryStoreForm(InventoryStoreForm form, Store store) {
        form.setStore(store);
        Date latestInventoryDate = serviceProvider.getService(IStoreService.class).getDateOfLatestInventoryStoreForm(
                store);
        Date latestClosingEntryDate = serviceProvider.getService(IStoreService.class).getDateOfLatestClosingStoreEntry(
                store);
        Set<DetailInventoryStore> detailSet = null;
        if (latestInventoryDate.after(latestClosingEntryDate)) {
            detailSet = initDetailInventoryStoreByInventory(store);
        } else {
            detailSet = initDetailInventoryStoreByClosingEntry(store);
        }
        form.getDetailInventoryStores().addAll(detailSet);
        return form;
    }

    public static void updateShipPrice(JComboBox cbShipPriceType, JTextField tfdShipNum, MoneyComponent mShipPrice) {
        ShipPriceType type = (ShipPriceType) cbShipPriceType.getSelectedItem();
        if (type != null) {
            Double shipnum = NumberUtils.createDouble(tfdShipNum.getText());

            ShipPrice shipPrice = serviceProvider.getService(IStoreService.class).getLatestShipPrice(type.getCode());
            Money shipPriceValue = shipPrice.getPrice();
            mShipPrice.setMoney(shipPriceValue.multiply(shipnum));
        } else {
            mShipPrice.setMoney(Money.create(CurrencyEnum.VND, 0L));
        }
    }

    private static Set<DetailExportStore> initDetailExportStore(Invoice invoice) {
        Set<DetailExportStore> result = new HashSet<DetailExportStore>();

        Set<DetailInvoice> invDetailSet = invoice.getDetailInvoices();
        Integer lineNo = 0;
        for (DetailInvoice invDetail : invDetailSet) {
            DetailExportStore exDetail = new DetailExportStore();
            // exDetail.setExportForm(form);
            exDetail.setLineNo(lineNo);
            exDetail.setProduct(invDetail.getProduct());
            exDetail.setItem(invDetail.getItem());
            exDetail.setReqQuan(invDetail.getAmount());
            result.add(exDetail);
            lineNo++;
        }
        return result;
    }

    private static Set<DetailExportStore> initDetailExportStore(ExportStoreForm latestForm) {
        Set<DetailExportStore> exDetailSet = new HashSet<DetailExportStore>();
        Integer lineNo = 0;
        List<DetailExportStore> detailList = serviceProvider.getService(IStoreService.class).getAllDetail(latestForm);
        for (DetailExportStore detail : detailList) {
            Integer reqQty = detail.getRemainQuan();
            if (reqQty > 0) {
                DetailExportStore exDetail = new DetailExportStore();
                exDetail.setLineNo(lineNo);
                exDetail.setProduct(detail.getProduct());
                exDetail.setProductName(detail.getProductName());
                exDetail.setItem(detail.getItem());
                exDetail.setUom(detail.getUom());
                exDetail.setBaseUom(detail.getBaseUom());
                exDetail.setReqQuan(reqQty);
                exDetail.setRemainQuan(reqQty);
                exDetailSet.add(exDetail);
                lineNo++;
            }
        }
        return exDetailSet;
    }

    private static Set<DetailInventoryStore> initDetailInventoryStoreByInventory(Store store) {
        InventoryStoreForm latestForm = serviceProvider.getService(IStoreService.class).getLatestInventoryStoreForm(
                store);
        Set<DetailInventoryStore> result = new HashSet<>();
        Integer lineNo = 0;
        for (DetailInventoryStore latestDetail : latestForm.getDetailInventoryStores()) {
            DetailInventoryStore detail = new DetailInventoryStore();
            detail.setLineNo(lineNo);
            detail.setProduct(latestDetail.getProduct());
            detail.setItem(latestDetail.getItem());
            detail.setBaseUom(latestDetail.getBaseUom());
            detail.setCurQty(latestDetail.getCurQty());
            detail.setPriceUnit(latestDetail.getPriceUnit());
            detail.setCurPriceSubtotal(latestDetail.getCurPriceSubtotal());
            result.add(detail);
            lineNo++;
        }
        return result;
    }

    private static Set<DetailInventoryStore> initDetailInventoryStoreByClosingEntry(Store store) {
        ClosingStoreEntry latestClosingEntry = serviceProvider.getService(IStoreService.class)
                .getLatestClosingStoreEntry(store);
        Set<DetailInventoryStore> result = new HashSet<>();
        Integer lineNo = 0;
        for (DetailClosingStore closingDetail : latestClosingEntry.getClosingStoreSet()) {
            DetailInventoryStore detail = new DetailInventoryStore();
            Product product = closingDetail.getProduct();
            Item item = closingDetail.getItem();
            Money priceUnit = item.getOriginPrice();
            Integer qty = closingDetail.getQty();
            detail.setLineNo(lineNo);
            detail.setProduct(product);
            detail.setItem(closingDetail.getItem());
            detail.setBaseUom(closingDetail.getBaseUom());
            detail.setCurQty(qty);
            detail.setPriceUnit(priceUnit);
            detail.setCurPriceSubtotal(priceUnit.multiply(qty));
            result.add(detail);
            lineNo++;
        }
        return result;
    }
}

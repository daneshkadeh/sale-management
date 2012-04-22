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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.view.ViewHelper;

/**
 * @author Le Thanh Hoang
 * 
 */
public class StoreHelper extends ViewHelper {

    public static Money calculatePriceSubtotal(String strQuantity, Money mPriceUnit) {
        int quan = NumberUtils.toInt(strQuantity);
        Money mPriceSubtotal = mPriceUnit.multiply(quan);
        return mPriceSubtotal;
    }

    public static ExportStoreForm initExportStoreForm(ExportStoreForm form, Invoice invoice) {
        form.setInvoice(invoice);
        form.setCustCode(invoice.getContact().getCode());
        form.setCustName(invoice.getContact().getName());
        switch (invoice.getStatus()) {
        case OPEN:
            form.getExportDetails().addAll(initDetailExportStore(invoice));
            break;
        case EXPORTING:
            ExportStoreForm latestForm = serviceProvider.getService(IStoreService.class).getLatestExportStoreForm(
                    invoice);
            form.getExportDetails().addAll(initDetailExportStore(latestForm));
            break;
        default:
            break;
        }
        return form;
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
        Set<DetailExportStore> exDetalSet = new HashSet<DetailExportStore>();
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
                exDetalSet.add(exDetail);
                lineNo++;
            }
        }
        return exDetalSet;
    }
}

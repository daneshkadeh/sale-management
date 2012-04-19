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
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.model.Money;

/**
 * @author Le Thanh Hoang
 * 
 */
public class StoreHelper {

    public static Money calculatePriceSubtotal(String strQuantity, Money mPriceUnit) {
        int quan = NumberUtils.toInt(strQuantity);
        Money mPriceSubtotal = mPriceUnit.multiply(quan);
        return mPriceSubtotal;
    }

    public static Set<DetailExportStore> initDetailExportStore(ExportStoreForm form, Invoice invoice) {
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

    public static Set<DetailExportStore> initDetailExportStore(ExportStoreForm form, Invoice invoice,
            ExportStoreForm latestForm) {
        Set<DetailExportStore> exDetalSet = new HashSet<DetailExportStore>();
        Integer lineNo = 0;
        for (DetailExportStore detail : latestForm.getExportDetails()) {
            Integer reqQty = detail.getRemainQuan();
            if (reqQty > 0) {
                DetailExportStore exDetail = new DetailExportStore();
                exDetail.setLineNo(lineNo);
                exDetail.setProduct(detail.getProduct());
                exDetail.setItem(detail.getItem());
                exDetail.setReqQuan(reqQty);
                exDetail.setRemainQuan(reqQty);
                exDetalSet.add(exDetail);
                lineNo++;
            }
        }
        return exDetalSet;
    }
}

/*
 * EditPaymentTypeView
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
package com.s3s.ssm.view.detail.finance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentView extends AbstractSingleEditView<Payment> {

    private static final String REF_PAYMENT_MODE = "0";
    private static final String REF_PAYMENT_CONTENT = "1";
    private static final String REF_CURRENCY = "currency";

    public EditPaymentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Payment entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("partnerCode", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("partnerName", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN).mandatory(true);
        detailDataModel.addAttribute("staffCode", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("staffName", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN).referenceDataId(REF_PAYMENT_CONTENT);
        detailDataModel.addAttribute("money", DetailFieldType.MONEY).referenceDataId(REF_CURRENCY);
        detailDataModel.addAttribute("rate", DetailFieldType.DROPDOWN);
        detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Payment entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PAYMENT_MODE, Arrays.asList(PaymentMode.values()), null);
        refDataModel.putRefDataList(REF_PAYMENT_CONTENT, getDaoHelper().getDao(PaymentContent.class).findAll(), null);

        // TODO: we will move list supported currencies to contextProvider
        List<SCurrency> listCurrencies = getDaoHelper().getDao(SCurrency.class).findAll();
        List<String> currencyCodes = new ArrayList<>();
        for (SCurrency currency : listCurrencies) {
            currencyCodes.add(currency.getCode());
        }
        refDataModel.putRefDataList(REF_CURRENCY, currencyCodes, null);
    }
}

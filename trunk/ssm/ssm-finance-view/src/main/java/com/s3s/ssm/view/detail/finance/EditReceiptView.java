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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditReceiptView extends AbstractSingleEditView<Payment> {
    private static final String REF_PAYMENT_MODE = "0";
    private static final String REF_PAYMENT_CONTENT = "1";
    private static final String REF_CURRENCY = "2";
    private static final String REF_PARTNER = "3";
    private static final String REF_OPERATOR = "4";

    public EditReceiptView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Payment entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("partner", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .referenceDataId(REF_PARTNER);
        detailDataModel.addAttribute("operator", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .referenceDataId(REF_OPERATOR);
        detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PAYMENT_CONTENT);
        detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN).referenceDataId(REF_PAYMENT_MODE);
        detailDataModel.addAttribute("money", DetailFieldType.MONEY).referenceDataId(REF_CURRENCY);
        detailDataModel.addAttribute("rate", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Payment entity) {
        // super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PAYMENT_MODE, Arrays.asList(PaymentMode.values()), null);

        // TODO: we will move list supported currencies to contextProvider
        List<SCurrency> listCurrencies = getDaoHelper().getDao(SCurrency.class).findAll();
        List<String> currencyCodes = new ArrayList<>();
        for (SCurrency currency : listCurrencies) {
            currencyCodes.add(currency.getCode());
        }
        refDataModel.putRefDataList(REF_CURRENCY, currencyCodes, null);

        DetachedCriteria dc = DetachedCriteria.forClass(PaymentContent.class).add(
                Property.forName("paymentType").eq(PaymentType.RECEIPT));
        refDataModel.putRefDataList(REF_PAYMENT_CONTENT,
                getDaoHelper().getDao(PaymentContent.class).findByCriteria(dc), null);
        refDataModel.putRefDataList(REF_PARTNER, getDaoHelper().getDao(Partner.class).findAll(), null);
        refDataModel.putRefDataList(REF_OPERATOR, getDaoHelper().getDao(Operator.class).findAll(), null);
    }
}

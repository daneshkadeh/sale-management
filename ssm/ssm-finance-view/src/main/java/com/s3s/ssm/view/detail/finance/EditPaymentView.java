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

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentView extends AbstractSingleEditView<Payment> {
    private static final String REF_PAYMENT_CONTENT = "1";
    private static final String REF_PAYMENT_MODE = "2";

    public EditPaymentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Payment entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("partner", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_PARTNER);
        detailDataModel.addAttribute("operator", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_OPERATOR);
        detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_PAYMENT_CONTENT);
        detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN).referenceDataId(REF_PAYMENT_MODE);
        detailDataModel.addAttribute("money", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("rate", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Payment entity) {
        // super.setReferenceDataModel(refDataModel, entity);

        DetachedCriteria dc = DetachedCriteria.forClass(PaymentContent.class).add(
                Property.forName("paymentType").eq(PaymentType.PAY));
        refDataModel.putRefDataList(REF_PAYMENT_CONTENT,
                getDaoHelper().getDao(PaymentContent.class).findByCriteria(dc), null);
        refDataModel.putRefDataList(REF_PAYMENT_MODE, Arrays.asList(PaymentMode.values()), null);

        // ReferenceData referenceData = refDataModel.new ReferenceData(
        // cacheDataService.getReferenceDataList(CacheId.REF_LIST_PAYMENT_CONTENT), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Payment loadForCreate() {
        Payment payment = super.loadForCreate();
        payment.setPaymentDate(new Date());
        return payment;
    }

}

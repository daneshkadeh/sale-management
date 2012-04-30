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

import java.util.Map;

import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentView extends AbstractSingleEditView<Payment> {
    private static final long serialVersionUID = -4763938512698675549L;

    public EditPaymentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Payment entity, Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("partner", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_PARTNER);
        detailDataModel.addAttribute("operator", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_OPERATOR);
        detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_PAYMENT_CONTENT);
        detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_PAYMENT_MODE);
        detailDataModel.addAttribute("amount", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("exchgValue", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("rate", DetailFieldType.TEXTBOX).editable(false).newColumn();
        detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, Payment entity) {
        super.customizeComponents(name2AttributeComponent, entity);
    }

}
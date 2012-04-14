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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.JComboBox;

import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentContentView extends AbstractSingleEditView<PaymentContent> {

    public EditPaymentContentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, PaymentContent entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("paymentType", DetailFieldType.DROPDOWN).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_PAYMENT_TYPE);
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTAREA).mandatory(true);
        detailDataModel.addAttribute("parent", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_PAYMENT_CONTENT);
    }

    // TODO: add listener for PaymentType. The values of parent is based on PaymentType
    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent, PaymentContent entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        JComboBox cbPaymentType = (JComboBox) name2AttributeComponent.get("paymentType").getComponent();
        final JComboBox parent = (JComboBox) name2AttributeComponent.get("parent").getComponent();
        cbPaymentType.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

            }
        });
    }
}

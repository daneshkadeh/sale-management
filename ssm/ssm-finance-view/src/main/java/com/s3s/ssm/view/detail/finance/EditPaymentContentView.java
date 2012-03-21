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
import java.util.Arrays;
import java.util.Map;

import javax.swing.JComboBox;

import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditPaymentContentView extends AbstractSingleEditView<PaymentContent> {

    private static final String REF_PAYMENT_TYPE = "0";
    private static final String REF_PAYMENT_CONTENT = "1";

    public EditPaymentContentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, PaymentContent entity) {
        detailDataModel.addAttribute("paymentType", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PAYMENT_TYPE);
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTAREA).mandatory(true);
        detailDataModel.addAttribute("parent", DetailFieldType.DROPDOWN).referenceDataId(REF_PAYMENT_CONTENT);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, PaymentContent entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PAYMENT_TYPE, Arrays.asList(PaymentType.values()), null);
        refDataModel.putRefDataList(REF_PAYMENT_CONTENT, getDaoHelper().getDao(PaymentContent.class).findAll(), null);
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

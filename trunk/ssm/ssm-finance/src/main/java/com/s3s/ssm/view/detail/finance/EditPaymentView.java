/*
 * EditPaymentView
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
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditPaymentView extends AbstractSingleEditView<Payment> {

    public EditPaymentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Payment entity) {
        // TODO Auto-generated method stub

    }

}

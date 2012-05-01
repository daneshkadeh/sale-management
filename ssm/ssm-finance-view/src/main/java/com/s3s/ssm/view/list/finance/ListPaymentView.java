/*
 * ListPaymentTypeView
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
package com.s3s.ssm.view.list.finance;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.view.detail.finance.EditPaymentView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListPaymentView extends AListEntityView<Payment> {
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("paymentDate", ListRendererType.TEXT);
        listDataModel.addColumn("partner", ListRendererType.TEXT);
        listDataModel.addColumn("operator", ListRendererType.TEXT);
        listDataModel.addColumn("paymentMode", ListRendererType.TEXT);
        listDataModel.addColumn("amount", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Payment>> getEditViewClass() {
        return EditPaymentView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.createAlias("paymentContent", "paymentContent");
        dc.add(Restrictions.eq("paymentContent.paymentType", PaymentType.PAY));
        return dc;
    }

}

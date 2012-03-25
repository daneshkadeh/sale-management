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
import com.s3s.ssm.view.detail.finance.EditReceiptView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListReceiptView extends AbstractListView<Payment> {
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("paymentDate", ListColumnType.TEXT);
        listDataModel.addColumn("partner", ListColumnType.TEXT);
        listDataModel.addColumn("operator", ListColumnType.TEXT);
        listDataModel.addColumn("paymentMode", ListColumnType.TEXT);
        listDataModel.addColumn("money", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Payment>> getEditViewClass() {
        return EditReceiptView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.createAlias("paymentContent", "paymentContent");
        dc.add(Restrictions.eq("paymentContent.paymentType", PaymentType.RECEIPT));
        return dc;
    }
}

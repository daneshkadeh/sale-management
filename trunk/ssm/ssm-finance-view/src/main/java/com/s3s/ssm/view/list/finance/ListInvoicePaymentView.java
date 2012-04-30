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

import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.view.detail.finance.EditInvoicePaymentView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInvoicePaymentView extends AbstractListView<InvoicePayment> {
    private static final long serialVersionUID = 7916303467956738315L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("paymentDate", ListRendererType.DATE);
        listDataModel.addColumn("partner", ListRendererType.TEXT);
        listDataModel.addColumn("operator", ListRendererType.TEXT);
        listDataModel.addColumn("paymentMode", ListRendererType.TEXT);
        listDataModel.addColumn("amount", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<InvoicePayment>> getEditViewClass() {
        return EditInvoicePaymentView.class;
    }
}

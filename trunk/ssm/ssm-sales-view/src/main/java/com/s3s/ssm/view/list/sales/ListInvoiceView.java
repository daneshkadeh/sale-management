/*
 * ListInvoiceView
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
package com.s3s.ssm.view.list.sales;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.view.detail.sales.EditInvoiceView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInvoiceView extends AbstractListView<Invoice> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("invoiceNumber", ListRendererType.TEXT);
        listDataModel.addColumn("type", ListRendererType.TEXT);
        listDataModel.addColumn("contact", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.TEXT);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT);
        listDataModel.addColumn("invoiceNumber", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
        listDataModel.addColumn("paymentStatus", ListRendererType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<Invoice>> getEditViewClass() {
        return EditInvoiceView.class;
    }

}

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
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListInvoiceView extends AbstractListView<Invoice> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("invoiceNumber", ListColumnType.TEXT);
        listDataModel.addColumn("type", ListColumnType.TEXT);
        listDataModel.addColumn("contact", ListColumnType.TEXT);
        listDataModel.addColumn("createdDate", ListColumnType.TEXT);
        listDataModel.addColumn("moneyAfterTax", ListColumnType.TEXT);
        listDataModel.addColumn("invoiceNumber", ListColumnType.TEXT);
        listDataModel.addColumn("status", ListColumnType.TEXT);
        listDataModel.addColumn("paymentStatus", ListColumnType.TEXT);

    }

    @Override
    protected Class<? extends AbstractEditView<Invoice>> getEditViewClass() {
        return EditInvoiceView.class;
    }

}

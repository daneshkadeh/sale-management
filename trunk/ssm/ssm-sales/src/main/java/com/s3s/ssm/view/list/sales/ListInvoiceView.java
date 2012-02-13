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

import java.util.List;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.sales.EditInvoiceView;

public class ListInvoiceView extends AbstractListView<Invoice> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("invoiceNumber", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("type", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("contact", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("createdDate", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("invoiceNumber", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("status", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("paymentStatus", FieldTypeEnum.TEXTBOX));

    }

    @Override
    protected Class<? extends AbstractEditView<Invoice>> getEditViewClass() {
        return EditInvoiceView.class;
    }

}
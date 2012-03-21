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

import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.view.detail.finance.EditPaymentView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListPaymentView extends AbstractListView<Payment> {

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("paymentDate", ListColumnType.TEXT);
        listDataModel.addColumn("partnerCode", ListColumnType.TEXT);
        listDataModel.addColumn("partnerName", ListColumnType.TEXT);
        listDataModel.addColumn("staffCode", ListColumnType.TEXT);
        listDataModel.addColumn("staffName", ListColumnType.TEXT);
        listDataModel.addColumn("paymentMode", ListColumnType.TEXT);
        listDataModel.addColumn("money", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Payment>> getEditViewClass() {
        return EditPaymentView.class;
    }

}

package com.s3s.ssm.view.detail.sales;

import javax.swing.Icon;

import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInvoicePaymentComponent extends AListComponent<InvoicePayment> {

    public ListInvoicePaymentComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("paymentDate", ListRendererType.DATE);
        listDataModel.addColumn("paymentMode", ListRendererType.TEXT);
        listDataModel.addColumn("amount", ListRendererType.TEXT);
    }

}

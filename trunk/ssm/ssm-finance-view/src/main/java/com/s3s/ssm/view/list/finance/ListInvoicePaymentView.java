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

import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;

import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.detail.finance.EditInvoicePaymentView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInvoicePaymentView extends ANonSearchListEntityView<InvoicePayment> {
    private static final long serialVersionUID = 7916303467956738315L;
    public static final String INVOICE_FORM = "invoiceForm";

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

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean preShowEditView(InvoicePayment entity, EditActionEnum action, Map<String, Object> detailParams) {
        boolean result = true;
        if (action == EditActionEnum.NEW) {
            String message = ControlConfigUtils.getString("label.ExportDialog.message");
            String title = ControlConfigUtils.getString("label.ExportDialog.title");
            String code = (String) JOptionPane.showInputDialog(this.getParent(), message, title,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (StringUtils.isEmpty(code)) {
                return false;
            }
            Invoice invoice = serviceProvider.getService(InvoiceService.class).findInvoiceByCode(code);
            if (invoice == null || !InvoiceType.SALES.equals(invoice.getType())) {
                return false;
            }
            switch (invoice.getPaymentStatus()) {
            case NO_PAYMENT:
            case DEPOSIT:
                detailParams.put(INVOICE_FORM, invoice);
                result = true;
                break;
            case BALANCED:
                message = ControlConfigUtils.getString("label.InformDialog.message");
                title = ControlConfigUtils.getString("label.InformDialog.title");
                JOptionPane.showMessageDialog(this.getParent(), message, title, JOptionPane.PLAIN_MESSAGE);
                result = false;
                break;
            default:
                throw new RuntimeException("Payment status of invoice is not supported");
            }
        }
        return result;
    }

}

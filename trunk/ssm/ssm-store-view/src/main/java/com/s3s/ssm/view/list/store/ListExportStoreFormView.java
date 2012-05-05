/*
 * ListExportStoreFormView
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
package com.s3s.ssm.view.list.store;

import java.util.Map;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.detail.store.EditExportStoreFormView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListExportStoreFormView extends ANonSearchListEntityView<ExportStoreForm> {
    private static final long serialVersionUID = 7393197060716188079L;
    public static final String DETAIL_SET = "detailSet";
    public static final String INVOICE_FORM = "invoiceForm";

    public ListExportStoreFormView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("status", ListRendererType.TEXT);
        listDataModel.addColumn("createdDate", ListRendererType.DATE);
        listDataModel.addColumn("store.name", ListRendererType.TEXT);
        listDataModel.addColumn("staff.fullName", ListRendererType.TEXT);
        listDataModel.addColumn("invoice.invoiceNumber", ListRendererType.LINK);
        listDataModel.addColumn("transType.name", ListRendererType.LINK);
        listDataModel.addColumn("transPrice", ListRendererType.TEXT);
        listDataModel.addColumn("isPrinted", ListRendererType.BOOLEAN);
        listDataModel.addColumn("reqQuanTotal", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH)
                .summarized();
        listDataModel.addColumn("realQuanTotal", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH)
                .summarized();
        listDataModel.addColumn("remainQuanTotal", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH)
                .summarized();
    }

    @Override
    protected Class<? extends AbstractEditView<ExportStoreForm>> getEditViewClass() {
        return EditExportStoreFormView.class;
    }

    @Override
    protected boolean preShowEditView(ExportStoreForm entity, EditActionEnum action, Map<String, Object> detailParams) {
        if (action == EditActionEnum.NEW) {
            String message = ControlConfigUtils.getString("label.ExportDialog.message");
            String title = ControlConfigUtils.getString("label.ExportDialog.title");
            String code = (String) JOptionPane.showInputDialog(this.getParent(), message, title,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (code == null) {
                return false;
            } else if ("".equals(code)) {
                return false;
            }
            Invoice invoice = serviceProvider.getService(InvoiceService.class).findInvoiceByCode(code);
            if (invoice == null || !InvoiceType.SALES.equals(invoice.getType())) {
                return false;
            }
            switch (invoice.getStoreStatus()) {
            case NO_ACTION:
                detailParams.put(INVOICE_FORM, invoice);
                break;
            case EXPORTING:
                detailParams.put(INVOICE_FORM, invoice);
                break;
            case EXPORTED:
                message = ControlConfigUtils.getString("label.InformDialog.message");
                title = ControlConfigUtils.getString("label.InformDialog.title");
                JOptionPane.showMessageDialog(this.getParent(), message, title, JOptionPane.PLAIN_MESSAGE);
                return false;
            default:
                return false;
            }
        }
        return true;
    }
}
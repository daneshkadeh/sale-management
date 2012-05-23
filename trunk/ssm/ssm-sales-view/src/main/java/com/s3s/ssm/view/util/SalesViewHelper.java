package com.s3s.ssm.view.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.view.ViewHelper;
import com.s3s.ssm.view.detail.sales.ListInvoiceExportStoreComponentView;
import com.s3s.ssm.view.detail.sales.ListInvoicePaymentComponent;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class SalesViewHelper extends ViewHelper {
    public static List<InvoicePayment> createPaymentComponentData(Invoice invoice) {
        if (!invoice.isPersisted()) {
            return Collections.emptyList();
        }
        return serviceProvider.getService(InvoiceService.class).getInvoicePayments(invoice);
    }

    public static IComponentInfo createPaymentComponentInfo() {
        ListInvoicePaymentComponent listPaymentComponent = new ListInvoicePaymentComponent(null, null, null);
        return new ListComponentInfo(listPaymentComponent, null);
    }

    public static IComponentInfo createInvoiceExportStoreComponentInfo() {
        ListInvoiceExportStoreComponentView listExportStoreComponent = new ListInvoiceExportStoreComponentView(
                new HashMap<String, Object>(), null, null, null);
        return new ListComponentInfo(listExportStoreComponent, null);
    }

    public static List<ExportStoreForm> createExportStoreComponentData(Invoice invoice) {
        if (!invoice.isPersisted()) {
            return Collections.emptyList();
        }
        return serviceProvider.getService(InvoiceService.class).getInvoiceExportStores(invoice);
    }
}

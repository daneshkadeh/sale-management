package com.s3s.ssm.interfaces.sales;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;

public interface InvoiceService {
    String getNextInvoiceNumber();

    List<Invoice> getAllInvoice();

    List<Invoice> getAllInvoiceBy(Date fromDate, Date toDate);

    List<Invoice> getAllInvoiceBy(Date fromDate);

    Invoice findInvoiceByCode(String code);

    List<InvoicePayment> getInvoicePayments(Invoice invoice);
}

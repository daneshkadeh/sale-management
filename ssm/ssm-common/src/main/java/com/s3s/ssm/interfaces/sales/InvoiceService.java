package com.s3s.ssm.interfaces.sales;

import java.util.List;

import com.s3s.ssm.entity.sales.Invoice;

public interface InvoiceService {
    String getNextInvoiceNumber();

    List<Invoice> getAllInvoice();

    Invoice findInvoiceByCode(String code);
}

package com.s3s.ssm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.interfaces.sales.InvoiceService;

@Transactional
@Service("invoiceServiceImpl")
public class InvoiceServiceImpl extends AbstractModuleServiceImpl implements InvoiceService {

    private static final String INVOICE_NUMBER_SEQ = "invoice_number_seq";

    @Override
    public void init() {
        serviceProvider.register(InvoiceService.class, this);
    }

    @Override
    public String getNextInvoiceNumber() {
        long nextNumber = getDaoHelper().getDao(Invoice.class).getNextSequence(INVOICE_NUMBER_SEQ);
        return String.valueOf(nextNumber);
    }

}

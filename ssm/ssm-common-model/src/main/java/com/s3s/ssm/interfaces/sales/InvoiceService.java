package com.s3s.ssm.interfaces.sales;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.sales.TopProductInMonthDTO;
import com.s3s.ssm.dto.sales.UnsoldProductByDayDTO;
import com.s3s.ssm.dto.sales.UnsoldProductBySoldQtyDTO;
import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.ExportStoreForm;

public interface InvoiceService {
    String getNextInvoiceNumber();

    List<Invoice> getAllInvoice();

    List<Invoice> getAllInvoiceBy(Date fromDate, Date toDate);

    List<Invoice> getAllInvoiceBy(Date fromDate);

    Invoice findInvoiceByCode(String code);

    List<InvoicePayment> getInvoicePayments(Invoice invoice);

    List<ExportStoreForm> getInvoiceExportStores(Invoice invoice);

    List<UnsoldProductByDayDTO> statisticUnsoldProductByDay();

    List<UnsoldProductBySoldQtyDTO> statisticUnsoldProductBySoldQty();

    List<TopProductInMonthDTO> statisticTopProductInMonth();
}

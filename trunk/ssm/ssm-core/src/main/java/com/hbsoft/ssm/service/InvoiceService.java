package com.hbsoft.ssm.service;

import java.util.List;

import com.hbsoft.ssm.entity.Invoice;

public interface InvoiceService {
    void save(Invoice invoice);

    void update(Invoice invoice);

    void delete(Invoice invoice);

    Invoice findById(Integer id);

    List<Invoice> findAll();
}

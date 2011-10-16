package com.s3s.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3s.ssm.dao.impl.InvoiceDaoImpl;
import com.s3s.ssm.entity.Invoice;
import com.s3s.ssm.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceDaoImpl invoiceDao;

    public void save(Invoice invoice) {
        invoiceDao.save(invoice);
    }

    public void update(Invoice invoice) {
        invoiceDao.update(invoice);
    }

    public void delete(Invoice invoice) {
        invoiceDao.delete(invoice);
    }

    public Invoice findById(Integer id) {
        return invoiceDao.findById(id);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }
}

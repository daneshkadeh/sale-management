package com.hbsoft.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.ssm.dao.InvoiceDao;
import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceDao invoiceDao;
	
	public void save(Invoice invoice) {
		invoiceDao.save(invoice);
	}

	public void update(Invoice invoice) {
		invoiceDao.update(invoice);
	}

	public void delete(Invoice invoice) {
		invoiceDao.delete(invoice);
	}

	public Invoice findById(Invoice id) {
		return invoiceDao.findById(id);
	}

}

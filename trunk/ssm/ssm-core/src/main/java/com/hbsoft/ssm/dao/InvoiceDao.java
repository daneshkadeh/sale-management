package com.hbsoft.ssm.dao;

import com.hbsoft.ssm.entity.Invoice;

public interface InvoiceDao {
	void save(Invoice invoice);

	void update(Invoice invoice);

	void delete(Invoice invoice);

	Invoice findById(Invoice id);
}

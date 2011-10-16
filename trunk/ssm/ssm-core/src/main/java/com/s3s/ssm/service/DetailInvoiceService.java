package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.entity.DetailInvoice;

public interface DetailInvoiceService {
	void save(DetailInvoice detailInvoice);

	void saveOrUpdate(List<DetailInvoice> listDetailInvoice);

	void update(DetailInvoice detailInvoice);

	void delete(DetailInvoice detailInvoice);

	DetailInvoice findById(Integer id);
}

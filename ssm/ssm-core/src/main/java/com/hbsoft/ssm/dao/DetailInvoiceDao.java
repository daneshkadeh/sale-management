package com.hbsoft.ssm.dao;

import java.util.List;

import com.hbsoft.ssm.entity.DetailInvoice;

public interface DetailInvoiceDao {
	void save(DetailInvoice detailInvoice);
	void saveOrUpdateAll(List<DetailInvoice> listDetailInvoice);
	void update(DetailInvoice detailInvoice);
	void delete(DetailInvoice detailInvoice);
	DetailInvoice findById(Integer id);
}

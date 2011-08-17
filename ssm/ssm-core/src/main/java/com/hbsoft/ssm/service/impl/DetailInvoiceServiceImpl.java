package com.hbsoft.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.ssm.dao.DetailInvoiceDao;
import com.hbsoft.ssm.entity.DetailInvoice;
import com.hbsoft.ssm.service.DetailInvoiceService;

@Service("detailInvoiceService")
public class DetailInvoiceServiceImpl implements DetailInvoiceService {

	@Autowired
	DetailInvoiceDao detailInvoiceDao;

	public void save(DetailInvoice detailInvoice) {
		detailInvoiceDao.save(detailInvoice);
	}

	public void saveOrUpdateAll(List<DetailInvoice> listDetailInvoice) {
		detailInvoiceDao.saveOrUpdateAll(listDetailInvoice);
	}

	public void update(DetailInvoice detailInvoice) {
		detailInvoiceDao.update(detailInvoice);
	}

	public void delete(DetailInvoice detailInvoice) {
		detailInvoiceDao.delete(detailInvoice);
	}

	public DetailInvoice findById(Integer id) {
		return detailInvoiceDao.findById(id);
	}

}

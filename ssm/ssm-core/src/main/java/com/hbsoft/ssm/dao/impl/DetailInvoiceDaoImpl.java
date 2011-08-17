package com.hbsoft.ssm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.dao.DetailInvoiceDao;
import com.hbsoft.ssm.entity.DetailInvoice;
import com.hbsoft.ssm.util.CustomHibernateDaoSupport;

@Repository("detailInvoiceDao")
public class DetailInvoiceDaoImpl extends CustomHibernateDaoSupport implements DetailInvoiceDao {

	public void save(DetailInvoice detailInvoice) {
		getHibernateTemplate().save(detailInvoice);
	}

	public void update(DetailInvoice detailInvoice) {
		getHibernateTemplate().update(detailInvoice);
	}

	public void delete(DetailInvoice detailInvoice) {
		getHibernateTemplate().delete(detailInvoice);
	}

	public DetailInvoice findById(Integer id) {
		List list = getHibernateTemplate().find("from DetailInvoice where id=?", id);
		return (DetailInvoice) list.get(0);
	}

	public void saveOrUpdateAll(List<DetailInvoice> listDetailInvoice) {
		getHibernateTemplate().saveOrUpdateAll(listDetailInvoice);
	}

}

package com.hbsoft.ssm.dao.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.dao.InvoiceDao;
import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.util.CustomHibernateDaoSupport;

@Repository("invoiceDao")
public class InvoiceDaoImpl extends CustomHibernateDaoSupport implements InvoiceDao {

	public void save(Invoice invoice) {
		getHibernateTemplate().save(invoice);
	}

	public void update(Invoice invoice) {
		getHibernateTemplate().update(invoice);
	}

	public void delete(Invoice invoice) {
		getHibernateTemplate().delete(invoice);
	}

	public Invoice findById(Invoice id) {
		List list = getHibernateTemplate().find("from Invoice where id=?", id);
		return (Invoice) list.get(0);
	}

	public Invoice findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Invoice> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Invoice> findLikeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(Collection<Invoice> collection) {
		// TODO Auto-generated method stub

	}

}

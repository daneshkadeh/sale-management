package com.hbsoft.ssm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.dao.InvoiceDao;
import com.hbsoft.ssm.entity.Invoice;
import com.hbsoft.ssm.util.CustomHibernateDaoSupport;

@Repository("invoiceDao")
public class InvoiceDaoImpl extends CustomHibernateDaoSupport implements
		InvoiceDao {

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
		List list = getHibernateTemplate().find("from Invoice where id=?",id);
		return (Invoice)list.get(0);
	}

}

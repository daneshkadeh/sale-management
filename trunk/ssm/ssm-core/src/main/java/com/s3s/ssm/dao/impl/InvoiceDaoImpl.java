package com.s3s.ssm.dao.impl;

import org.springframework.stereotype.Repository;

import com.s3s.ssm.entity.Invoice;

@Repository("invoiceDao")
public class InvoiceDaoImpl extends HibernateBaseDaoImpl<Invoice> {

}

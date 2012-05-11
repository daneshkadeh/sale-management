package com.s3s.ssm.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.interfaces.sales.InvoiceService;

@Transactional
@Service("invoiceServiceImpl")
public class InvoiceServiceImpl extends AbstractModuleServiceImpl implements InvoiceService {

    private static final String INVOICE_NUMBER_SEQ = "invoice_number_seq";

    @Override
    public void init() {
        serviceProvider.register(InvoiceService.class, this);
    }

    @Override
    public String getNextInvoiceNumber() {
        long nextNumber = getDaoHelper().getDao(Invoice.class).getNextSequence(INVOICE_NUMBER_SEQ);
        return String.valueOf(nextNumber);
    }

    @Override
    public List<Invoice> getAllInvoice() {
        DetachedCriteria dc = getDaoHelper().getDao(Invoice.class).getCriteria();
        dc.add(Restrictions.not(Restrictions.eq("status", InvoiceStatus.ABANDONED)));
        dc.addOrder(Order.desc("createdDate"));
        List<Invoice> result = getDaoHelper().getDao(Invoice.class).findByCriteria(dc);
        return result;
    }

    public List<Invoice> getAllInvoiceBy(Date fromDate, Date toDate) {
        DetachedCriteria dc = getDaoHelper().getDao(Invoice.class).getCriteria();
        dc.add(Restrictions.not(Restrictions.eq("status", InvoiceStatus.ABANDONED)));
        dc.add(Restrictions.between("createdDate", fromDate, toDate));
        List<Invoice> result = getDaoHelper().getDao(Invoice.class).findByCriteria(dc);
        return result;
    }

    public List<Invoice> getAllInvoiceBy(Date fromDate) {
        return getAllInvoiceBy(fromDate, new Date());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Invoice findInvoiceByCode(String code) {
        DetachedCriteria dc = getDaoHelper().getDao(Invoice.class).getCriteria();
        dc.add(Restrictions.not(Restrictions.eq("status", InvoiceStatus.ABANDONED)));
        dc.add(Restrictions.eq("invoiceNumber", code));
        Invoice invoice = getDaoHelper().getDao(Invoice.class).findFirstByCriteria(dc);
        return invoice;
    }

    public List<InvoicePayment> getInvoicePayments(Invoice invoice) {
        DetachedCriteria dc = getDaoHelper().getDao(InvoicePayment.class).getCriteria();
        dc.add(Restrictions.eq("invoice", invoice));
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getDaoHelper().getDao(InvoicePayment.class).findByCriteria(dc);
    }
}

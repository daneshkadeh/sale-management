package com.s3s.ssm.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.finance.ClosingFinanceEntry;
import com.s3s.ssm.entity.finance.DetailClosingFinance;
import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentMode;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.interfaces.contact.IContactService;
import com.s3s.ssm.interfaces.finance.IFinanceService;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("financeServiceImpl")
public class FinanceServiceImpl extends AbstractModuleServiceImpl implements IFinanceService {

    @Override
    public void init() {
        serviceProvider.register(IFinanceService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_PAYMENT_CONTENT, this,
                    this.getClass().getMethod("getPaymentContents"));
            getCacheDataService().registerCache(CacheId.REF_LIST_RECEIPT_CONTENT, this,
                    this.getClass().getMethod("getReceiptContents"));
            getCacheDataService().registerCache(CacheId.REF_LIST_PAYMENT_MODE, this,
                    this.getClass().getMethod("getPaymentModes"));
            getCacheDataService().registerCache(CacheId.REF_LIST_PAYMENT_TYPE, this,
                    this.getClass().getMethod("getPaymentTypes"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PaymentContent> getPaymentContents() {
        DetachedCriteria dc = DetachedCriteria.forClass(PaymentContent.class).add(
                Property.forName("paymentType").eq(PaymentType.PAY));
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<PaymentContent> pamentContent = getDaoHelper().getDao(PaymentContent.class).findByCriteria(dc);
        return pamentContent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PaymentContent> getReceiptContents() {
        DetachedCriteria dc = DetachedCriteria.forClass(PaymentContent.class).add(
                Property.forName("paymentType").eq(PaymentType.RECEIPT));
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<PaymentContent> receiptContent = getDaoHelper().getDao(PaymentContent.class).findByCriteria(dc);
        return receiptContent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PaymentMode> getPaymentModes() {
        return Arrays.asList(PaymentMode.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PaymentType> getPaymentTypes() {
        return Arrays.asList(PaymentType.values());
    }

    @Override
    public void createInvoicePayment(Invoice invoice, PaymentContent paymentContent, PaymentMode paymentMode,
            Money amount) {
        InvoicePayment payment = new InvoicePayment();
        payment.setPaymentContent(paymentContent);
        payment.setPartner(invoice.getContact());
        payment.setOperator(invoice.getStaff());
        payment.setPaymentMode(paymentMode);
        payment.setAmount(amount);
        getDaoHelper().getDao(InvoicePayment.class).save(payment);
    }

    public void processClosingFinanceEntry() {
        List<Partner> partnerList = serviceProvider.getService(IContactService.class).getPartners();
        ClosingFinanceEntry newClosingEntry = new ClosingFinanceEntry();
        Set<DetailClosingFinance> newDetailSet = new HashSet<DetailClosingFinance>();
        for (Partner partner : partnerList) {
            DetailClosingFinance newDetail = new DetailClosingFinance();
            newDetail.setClosingEntry(newClosingEntry);
            newDetail.setPartner(partner);
        }
    }

    private ClosingFinanceEntry getLatestClosingFinanceEntry() {
        DetachedCriteria subselectDc = getDaoHelper().getDao(ClosingFinanceEntry.class).getCriteria();
        subselectDc.setProjection(Property.forName("closingDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(ClosingFinanceEntry.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.add(Property.forName("closingDate").eq(subselectDc));
        ClosingFinanceEntry closingFinanceEntry = getDaoHelper().getDao(ClosingFinanceEntry.class).findFirstByCriteria(
                dc);
        return closingFinanceEntry;
    }

    private void updateDetailClosingFinanceByPayment() {

    }
}

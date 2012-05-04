package com.s3s.ssm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.finance.CustomerDebtHistoryDTO;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.finance.ClosingFinanceEntry;
import com.s3s.ssm.entity.finance.DetailClosingFinance;
import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentMode;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.interfaces.contact.IContactService;
import com.s3s.ssm.interfaces.finance.IFinanceService;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.model.CurrencyEnum;
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
        getDaoHelper().getDao(InvoicePayment.class).saveOrUpdate(payment);
    }

    public void processClosingFinanceEntry() {
        List<Partner> partnerList = serviceProvider.getService(IContactService.class).getPartners();
        ClosingFinanceEntry newClosingEntry = new ClosingFinanceEntry();
        Set<DetailClosingFinance> newDetailSet = new HashSet<DetailClosingFinance>();

        for (Partner partner : partnerList) {
            DetailClosingFinance newDetail = new DetailClosingFinance();
            newDetail.setClosingEntry(newClosingEntry);
            newDetail.setPartner(partner);
            newDetail.setDebt(partner.getContactDebt().getDebtMoney());
        }

        // ClosingFinanceEntry closingEntry = getLatestClosingFinanceEntry();
        // updateDetailClosingFinanceByLastClosingFinanceEntry(newDetailSet, closingEntry);
        // updateDetailClosingFinanceByPayment(newDetailSet, closingEntry.getClosingDate());
        // updateDetailClosingFinanceByInvoice(newDetailSet, closingEntry.getClosingDate());

        getDaoHelper().getDao(ClosingFinanceEntry.class).saveOrUpdate(newClosingEntry);
    }

    @Override
    public List<CustomerDebtHistoryDTO> getDebtHistory(String partnerCode, Date fromDate, Date toDate) {
        List<CustomerDebtHistoryDTO> result = new ArrayList<CustomerDebtHistoryDTO>();
        // get data from invoice
        DetachedCriteria invoiceDc = getDaoHelper().getDao(Invoice.class).getCriteria();
        ProjectionList invoiceProjList = Projections.projectionList();

        invoiceProjList.add(Projections.property("createdDate"), "hisDate");
        invoiceProjList.add(Projections.property("invoiceNumber"), "code");
        invoiceProjList.add(Projections.property("type"), "contentType");
        invoiceProjList.add(Projections.property("moneyAfterTax.value"), "amt");
        invoiceDc.setProjection(invoiceProjList);
        invoiceDc.setResultTransformer(new AliasToBeanResultTransformer(CustomerDebtHistoryDTO.class));
        List resultFromInvoice = (List) getDaoHelper().getDao(Invoice.class).findByCriteria(invoiceDc);
        result.addAll(resultFromInvoice);

        // get data from payment
        DetachedCriteria paymentDc = getDaoHelper().getDao(Payment.class).getCriteria();
        ProjectionList paymentProjList = Projections.projectionList();
        paymentDc.createAlias("paymentContent", "paymentContent");
        paymentProjList.add(Projections.property("paymentDate"), "hisDate");
        paymentProjList.add(Projections.property("code"), "code");
        paymentProjList.add(Projections.property("paymentContent.paymentType"), "contentType");
        paymentProjList.add(Projections.property("paymentContent.name"), "content");
        paymentProjList.add(Projections.property("amount.value"), "payAmt");
        paymentDc.add(Restrictions.eq("paymentContent.paymentType", PaymentType.RECEIPT));
        paymentDc.setProjection(paymentProjList);
        paymentDc.setResultTransformer(new AliasToBeanResultTransformer(CustomerDebtHistoryDTO.class));
        List resultFromPayment = (List) getDaoHelper().getDao(Payment.class).findByCriteria(paymentDc);
        result.addAll(resultFromPayment);

        // get data from receipt
        DetachedCriteria receiptDc = getDaoHelper().getDao(Payment.class).getCriteria();
        ProjectionList receiptProjList = Projections.projectionList();
        receiptDc.createAlias("paymentContent", "paymentContent");
        receiptProjList.add(Projections.property("paymentDate"), "hisDate");
        receiptProjList.add(Projections.property("code"), "code");
        receiptProjList.add(Projections.property("paymentContent.paymentType"), "contentType");
        receiptProjList.add(Projections.property("paymentContent.name"), "content");
        receiptProjList.add(Projections.property("amount.value"), "advanceAmt");
        receiptDc.add(Restrictions.eq("paymentContent.paymentType", PaymentType.PAY));
        receiptDc.setProjection(receiptProjList);
        receiptDc.setResultTransformer(new AliasToBeanResultTransformer(CustomerDebtHistoryDTO.class));
        List resultFromReceipt = (List) getDaoHelper().getDao(Payment.class).findByCriteria(receiptDc);
        result.addAll(resultFromReceipt);
        return result;
    }

    @Override
    public Money getFirstTermDebt(Partner partner, Date date) {
        // DetachedCriteria subselectDc = getDaoHelper().getDao(DetailClosingFinance.class).getCriteria();
        // subselectDc.add(Restrictions.le("closingEntry.closingDate", date));
        // subselectDc.setProjection(Property.forName("closingEntry.closingDate").max());
        //
        // DetachedCriteria dc = getDaoHelper().getDao(DetailClosingFinance.class).getCriteria();
        // dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        // dc.add(Property.forName("closingEntry.closingDate").eq(subselectDc));
        // DetailClosingFinance detail = getDaoHelper().getDao(DetailClosingFinance.class).findFirstByCriteria(dc);
        return Money.create(CurrencyEnum.VND, 0L);
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

    private ClosingFinanceEntry getLatestClosingFinanceEntry(Date date) {
        DetachedCriteria subselectDc = getDaoHelper().getDao(ClosingFinanceEntry.class).getCriteria();
        subselectDc.add(Restrictions.le("closingDate", date));
        subselectDc.setProjection(Property.forName("closingDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(ClosingFinanceEntry.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.add(Property.forName("closingDate").eq(subselectDc));
        ClosingFinanceEntry closingFinanceEntry = getDaoHelper().getDao(ClosingFinanceEntry.class).findFirstByCriteria(
                dc);
        return closingFinanceEntry;
    }

    private List<Payment> getPayments(Date fromDate) {
        return getPayments(fromDate, new Date());
    }

    private List<Payment> getPayments(Date fromDate, Date toDate) {
        DetachedCriteria dc = getDaoHelper().getDao(Payment.class).getCriteria();
        dc.add(Restrictions.between("paymentDate", fromDate, toDate));
        return getDaoHelper().getDao(Payment.class).findByCriteria(dc);
    }

    private void updateDetailClosingFinanceByLastClosingFinanceEntry(Set<DetailClosingFinance> detailSet,
            ClosingFinanceEntry closingEntry) {
        for (DetailClosingFinance newDetail : detailSet) {
            Partner partner = newDetail.getPartner();
            for (DetailClosingFinance lastDetail : closingEntry.getClosingFinanceSet()) {
                if (partner.equals(lastDetail.getPartner())) {
                    newDetail.setDebt(lastDetail.getDebt());
                }
            }
        }
    }

    private void updateDetailClosingFinanceByPayment(Set<DetailClosingFinance> detailSet, Date fromDate) {
        updateDetailClosingFinanceByPayment(detailSet, fromDate, new Date());
    }

    private void updateDetailClosingFinanceByPayment(Set<DetailClosingFinance> detailSet, Date fromDate, Date toDate) {
        List<Payment> payments = getPayments(fromDate);
        for (DetailClosingFinance detail : detailSet) {
            Partner partner = detail.getPartner();
            for (Payment payment : payments) {
                if (partner.equals(payment.getPartner())) {
                    PaymentType paymentType = payment.getPaymentContent().getPaymentType();
                    Money oldDebt = detail.getDebt();
                    Money newDebt = null;
                    switch (paymentType) {
                    case PAY:// pay for supplier or customer in case of refund
                        if (partner.isSupplier()) {
                            newDebt = oldDebt.minus(payment.getAmount());
                        } else {
                            newDebt = oldDebt.plus(payment.getAmount());
                        }
                        break;
                    case RECEIPT:// receipt from customer or supplier in case of refund
                        if (partner.isSupplier()) {
                            newDebt = oldDebt.plus(payment.getAmount());
                        } else {
                            newDebt = oldDebt.minus(payment.getAmount());
                        }
                        break;
                    }
                    detail.setDebt(newDebt);
                }
            }
        }
    }

    private void updateDetailClosingFinanceByInvoice(Set<DetailClosingFinance> detailSet, Date fromDate) {
        updateDetailClosingFinanceByInvoice(detailSet, fromDate, new Date());
    }

    private void updateDetailClosingFinanceByInvoice(Set<DetailClosingFinance> detailSet, Date fromDate, Date toDate) {
        List<Invoice> invoices = serviceProvider.getService(InvoiceService.class).getAllInvoiceBy(fromDate, toDate);
        for (DetailClosingFinance detail : detailSet) {
            Partner partner = detail.getPartner();
            for (Invoice invoice : invoices) {
                InvoiceType type = invoice.getType();
                if (partner.equals(invoice.getContact()) && partner.isCustomer()) {
                    Money oldValue = detail.getDebt();
                    Money newValue = null;
                    switch (type) {
                    case SALES:// only customer
                        newValue = oldValue.plus(invoice.getMoneyAfterTax());
                        detail.setDebt(newValue);
                        break;
                    case REFUND:// only customer
                        newValue = oldValue.minus(invoice.getMoneyAfterTax());
                        break;
                    default:
                        break;
                    }
                    detail.setDebt(newValue);
                }
            }
        }
    }

    private void updateDetailClosingFinanceBySalesContract() {

    }
}

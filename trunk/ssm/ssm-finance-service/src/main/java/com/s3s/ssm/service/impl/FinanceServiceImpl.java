package com.s3s.ssm.service.impl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentMode;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.interfaces.finance.IFinanceService;
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
}

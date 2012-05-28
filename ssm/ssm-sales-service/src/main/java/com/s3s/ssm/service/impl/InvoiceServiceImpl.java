package com.s3s.ssm.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.sales.TopProductInMonthDTO;
import com.s3s.ssm.dto.sales.UnsoldProductByDayDTO;
import com.s3s.ssm.dto.sales.UnsoldProductBySoldQtyDTO;
import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.store.ExportStoreForm;
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

    @Override
    public List<ExportStoreForm> getInvoiceExportStores(Invoice invoice) {
        DetachedCriteria dc = getDaoHelper().getDao(ExportStoreForm.class).getCriteria();
        dc.add(Restrictions.eq("invoice", invoice));
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getDaoHelper().getDao(ExportStoreForm.class).findByCriteria(dc);
    }

    @Override
    public List<UnsoldProductByDayDTO> statisticUnsoldProductByDay() {
        // creating dummy data
        UnsoldProductByDayDTO dto1 = new UnsoldProductByDayDTO();
        dto1.setGoodsCode("WRT 7101");
        dto1.setGoodsName("Vot Tennis Wilson Five BLX");
        dto1.setLatestSellDate(DateTime.now().minusMonths(1).toDate());
        dto1.setMustSoldPeriod(15L);
        dto1.setUnsoldDayNum(15);

        UnsoldProductByDayDTO dto2 = new UnsoldProductByDayDTO();
        dto2.setGoodsCode("Z4704");
        dto2.setGoodsName("Da quan can AIRE Overgrip");
        dto2.setLatestSellDate(DateTime.now().minusMonths(2).toDate());
        dto2.setMustSoldPeriod(30L);
        dto2.setUnsoldDayNum(30);

        return Arrays.asList(dto1, dto2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UnsoldProductBySoldQtyDTO> statisticUnsoldProductBySoldQty() {
        return Collections.EMPTY_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TopProductInMonthDTO> statisticTopProductInMonth() {
        return Collections.EMPTY_LIST;
    }
}

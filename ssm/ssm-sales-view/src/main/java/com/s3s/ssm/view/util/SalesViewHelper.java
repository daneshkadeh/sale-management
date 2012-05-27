package com.s3s.ssm.view.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.contact.CustomerProfile;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.view.ViewHelper;
import com.s3s.ssm.view.detail.sales.ListInvoiceExportStoreComponentView;
import com.s3s.ssm.view.detail.sales.ListInvoicePaymentComponent;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;

public class SalesViewHelper extends ViewHelper {
    public static List<InvoicePayment> createPaymentComponentData(Invoice invoice) {
        if (!invoice.isPersisted()) {
            return Collections.emptyList();
        }
        return serviceProvider.getService(InvoiceService.class).getInvoicePayments(invoice);
    }

    public static IComponentInfo createPaymentComponentInfo() {
        ListInvoicePaymentComponent listPaymentComponent = new ListInvoicePaymentComponent(null, null, null);
        return new ListComponentInfo(listPaymentComponent, null);
    }

    public static IComponentInfo createInvoiceExportStoreComponentInfo() {
        ListInvoiceExportStoreComponentView listExportStoreComponent = new ListInvoiceExportStoreComponentView(
                new HashMap<String, Object>(), null, null, null);
        return new ListComponentInfo(listExportStoreComponent, null);
    }

    public static List<ExportStoreForm> createExportStoreComponentData(Invoice invoice) {
        if (!invoice.isPersisted()) {
            return Collections.emptyList();
        }
        return serviceProvider.getService(InvoiceService.class).getInvoiceExportStores(invoice);
    }

    public static Money getMinimumPrice(Item item, Partner contact) {
        if (item == null) {
            return Money.zero(CurrencyEnum.VND);
        }
        if (contact == null) {
            return item.getBaseSellPrice();
        }
        CustomerProfile customerProfile = (CustomerProfile) contact.getPartnerProfile(PartnerProfileTypeEnum.CUSTOMER);
        if (customerProfile == null || customerProfile.getAudienceCates().isEmpty()) {
            return item.getBaseSellPrice();
        }

        DetachedCriteria dc = daoHelper.getDao(ItemPrice.class).getCriteria();
        dc.add(Restrictions.eq("item", item));
        dc.add(Restrictions.in("audienceCategory", customerProfile.getAudienceCates()));
        List<ItemPrice> itemPrices = daoHelper.getDao(ItemPrice.class).findByCriteria(dc);
        if (CollectionUtils.isEmpty(itemPrices)) {
            return item.getBaseSellPrice();
        }
        // get the min itemPrice
        Money minPrice = item.getBaseSellPrice();
        for (ItemPrice price : itemPrices) {
            if (minPrice.compareTo(price.getSellPrice()) > 0) {
                minPrice = price.getSellPrice();
            }
        }
        return minPrice;
    }

    public static InvoicePayment getFirstInvoicePayment(Invoice entity) {
        if (entity == null || !entity.isPersisted()) {
            return null;
        }
        DetachedCriteria dc = daoHelper.getDao(InvoicePayment.class).getCriteria();
        dc.add(Restrictions.eq("invoice", entity));
        dc.add(Restrictions.ne("prePaidAmt.value", 0L));
        return daoHelper.getDao(InvoicePayment.class).findFirstByCriteria(dc);
    }
}

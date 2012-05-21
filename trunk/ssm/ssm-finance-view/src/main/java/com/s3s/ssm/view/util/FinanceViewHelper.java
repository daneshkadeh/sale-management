/*
 * StoreUtil
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.util;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.finance.CustDebtHistoryDTO;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.interfaces.finance.IFinanceService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.view.ViewHelper;

/**
 * @author Le Thanh Hoang
 * 
 */
public class FinanceViewHelper extends ViewHelper {
    public static void transformDebtHistory(List<CustDebtHistoryDTO> debtHistDTOList, Partner partner, Date fromDate,
            Date toDate) {
        CustDebtHistoryDTO firstPeriodDebt = new CustDebtHistoryDTO();

        Money firstPeriodDebtAmt = serviceProvider.getService(IFinanceService.class)
                .getFirstTermDebt(partner, fromDate);
        firstPeriodDebt.setHisDate(fromDate);
        // TODO: must use i18n
        firstPeriodDebt.setContent("Cong no dau ki");
        firstPeriodDebt.setDebtAmt(firstPeriodDebtAmt.getValue());
        debtHistDTOList.add(0, firstPeriodDebt);
        for (int i = 1; i < debtHistDTOList.size(); i++) {
            Long preDebt = debtHistDTOList.get(i - 1).getDebtAmt();
            CustDebtHistoryDTO dto = debtHistDTOList.get(i);
            if (partner.isCustomer()) {
                Long curDebt = preDebt + dto.getAmt() - dto.getPayAmt() + dto.getAdvanceAmt();
                dto.setDebtAmt(curDebt);
            }
            if (partner.isSupplier()) {

            }
        }
        CustDebtHistoryDTO lastPeriodDebt = new CustDebtHistoryDTO();
        lastPeriodDebt.setHisDate(toDate);
        // TODO: must use i18n
        lastPeriodDebt.setContent("Cong no cuoi ki");
        Long lastDebt = debtHistDTOList.get(debtHistDTOList.size() - 1).getDebtAmt();
        lastPeriodDebt.setDebtAmt(lastDebt);
        debtHistDTOList.add(lastPeriodDebt);
    }

    public static void initInvoicePayment(InvoicePayment invPayment, final Invoice invoice) {
        invPayment.setInvoice(invoice);
        // get debt of customer
        Partner customer = invoice.getContact();
        Money custDebt = customer.getContactDebt().getDebtMoney();
        invPayment.setCustDebt(custDebt);
        invPayment.setPartner(customer);
        invPayment.setInvoice(invoice);
        switch (invoice.getPaymentStatus()) {
        case NO_PAYMENT:
            invPayment.setPrePaidAmt(Money.create(CurrencyEnum.VND, 0L));
            invPayment.setRemainingAmt(invoice.getMoneyAfterTax());
            invoice.setPaymentStatus(InvoicePaymentStatus.DEPOSIT);
            break;
        case DEPOSIT:
            // get all of last payment for invoice
            Money prePaidAmt = serviceProvider.getService(IFinanceService.class).getPayAmt4Invoice(invoice);
            invPayment.setPrePaidAmt(prePaidAmt);
            Money invAmt = invoice.getMoneyAfterTax();
            Money remainingAmt = invAmt.minus(prePaidAmt);
            invPayment.setRemainingAmt(remainingAmt);
            break;
        default:
            break;
        }
    }
}

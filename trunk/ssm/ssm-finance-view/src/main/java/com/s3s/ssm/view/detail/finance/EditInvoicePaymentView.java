/*
 * EditPaymentTypeView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.detail.finance;

import java.util.Map;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.s3s.ssm.entity.finance.InvoicePayment;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.finance.ListInvoicePaymentView;
import com.s3s.ssm.view.util.FinanceViewHelper;

public class EditInvoicePaymentView extends AbstractSingleEditView<InvoicePayment> {
    private static final long serialVersionUID = -3334854367548540471L;

    public EditInvoicePaymentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, InvoicePayment entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("custDebt", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY)
                .editable(false);
        detailDataModel.addAttribute("prePaidAmt", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY)
                .editable(false);
        detailDataModel.addAttribute("remainingAmt", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY)
                .editable(false);
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("invoice.invoiceNumber", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("partner", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("operator", DetailFieldType.SEARCHER).mandatory(true)
                .componentInfo(ComponentFactory.createAccountantComponentInfo());
        detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_RECEIPT_CONTENT);

        detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_PAYMENT_MODE);
        detailDataModel.addAttribute("amount", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("rate", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent,
            final InvoicePayment entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JTextField tdfRate = (JTextField) name2AttributeComponent.get("rate").getComponent();
        final MoneyComponent mc = (MoneyComponent) name2AttributeComponent.get("amount").getComponent();
        mc.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Money money = mc.getMoney();
                CurrencyEnum currencyCode = money.getCurrencyCode();
                Double rate = serviceProvider.getService(IConfigService.class).getExchangeRate(currencyCode,
                        entity.getPaymentDate());
                tdfRate.setText(rate.toString());
            }
        });

    }

    @Override
    protected InvoicePayment loadForCreate(Map<String, Object> request) {
        InvoicePayment entity = super.loadForCreate(request);
        if (request.get("invoice") != null) {
            entity.setInvoice((Invoice) request.get("invoice"));
        }
        if (entity.getInvoice() != null) {
            entity.setPartner(entity.getInvoice().getContact());
        }
        Invoice invoice = (Invoice) request.get(ListInvoicePaymentView.INVOICE_FORM);
        FinanceViewHelper.initInvoicePayment(entity, invoice);
        return entity;
    }

}

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.finance.ContractPayment;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.interfaces.config.ConfigService;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.component.IMoneyChangedListener;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditContractPaymentView extends AbstractSingleEditView<ContractPayment> {
    private static final String REF_PAYMENT_MODE = "0";
    private static final String REF_PAYMENT_CONTENT = "1";
    private static final String REF_CURRENCY = "2";
    private static final String REF_PARTNER = "3";
    private static final String REF_OPERATOR = "4";
    private static final String REF_SALES_CONTRACT = "5";

    public EditContractPaymentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ContractPayment entity) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("partner", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .referenceDataId(REF_PARTNER);
        detailDataModel.addAttribute("operator", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .referenceDataId(REF_OPERATOR);
        detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PAYMENT_CONTENT);
        detailDataModel.addAttribute("salesContract", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .referenceDataId(REF_SALES_CONTRACT);
        detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN).referenceDataId(REF_PAYMENT_MODE);
        detailDataModel.addAttribute("money", DetailFieldType.MONEY).referenceDataId(REF_CURRENCY);
        detailDataModel.addAttribute("rate", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent,
            final ContractPayment entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JTextField tdfRate = (JTextField) name2AttributeComponent.get("rate").getComponent();
        final MoneyComponent mc = (MoneyComponent) name2AttributeComponent.get("money").getComponent();
        mc.addMoneyChangeListener(new IMoneyChangedListener() {
            @Override
            public void doMoneyChanged(ChangeEvent e) {
                Money money = mc.getMoney();
                String currencyCode = money.getCurrencyCode();
                Double rate = serviceProvider.getService(ConfigService.class).getExchangeRate(currencyCode,
                        entity.getPaymentDate());
                tdfRate.setText(rate.toString());
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContractPayment loadForCreate() {
        ContractPayment contractPayment = super.loadForCreate();
        String code = serviceProvider.getService(ConfigService.class).generateCode(Payment.class);
        contractPayment.setCode(code);
        contractPayment.setPaymentDate(new Date());
        return contractPayment;
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ContractPayment entity) {
        // super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_PAYMENT_MODE, Arrays.asList(PaymentMode.values()), null);

        // TODO: we will move list supported currencies to contextProvider
        List<SCurrency> listCurrencies = getDaoHelper().getDao(SCurrency.class).findAll();
        List<String> currencyCodes = new ArrayList<>();
        for (SCurrency currency : listCurrencies) {
            currencyCodes.add(currency.getCode());
        }
        refDataModel.putRefDataList(REF_CURRENCY, currencyCodes, null);

        DetachedCriteria dc = DetachedCriteria.forClass(PaymentContent.class).add(
                Property.forName("paymentType").eq(PaymentType.PAY));
        refDataModel.putRefDataList(REF_PAYMENT_CONTENT,
                getDaoHelper().getDao(PaymentContent.class).findByCriteria(dc), null);
        refDataModel.putRefDataList(REF_PARTNER, getDaoHelper().getDao(Partner.class).findAll(), null);
        refDataModel.putRefDataList(REF_OPERATOR, getDaoHelper().getDao(Operator.class).findAll(), null);
        refDataModel.putRefDataList(REF_SALES_CONTRACT, getDaoHelper().getDao(SalesContract.class).findAll(), null);
    }
}

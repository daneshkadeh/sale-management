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

import com.s3s.ssm.entity.finance.ContractPayment;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.component.IValueChangedListener;
import com.s3s.ssm.view.component.MoneyComponent;
import com.s3s.ssm.view.component.PartnerSearchComponent;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.SearchComponentInfo;

public class EditContractPaymentView extends AbstractSingleEditView<ContractPayment> {
    private static final long serialVersionUID = -3334854367548540471L;
    private static final String REF_SALES_CONTRACT = "1";

    public EditContractPaymentView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ContractPayment entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("paymentDate", DetailFieldType.DATE).mandatory(true);

        detailDataModel.addAttribute("partner", DetailFieldType.SEARCHER).mandatory(true)
                .componentInfo(createPartnerSearchInfo());
        detailDataModel.addAttribute("operator", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_OPERATOR);
        detailDataModel.addAttribute("paymentContent", DetailFieldType.DROPDOWN).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_PAYMENT_CONTENT);
        detailDataModel.addAttribute("salesContract", DetailFieldType.ENTITY_CHOOSER).mandatory(true)
                .referenceDataId(REF_SALES_CONTRACT);
        detailDataModel.addAttribute("paymentMode", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_PAYMENT_MODE);
        detailDataModel.addAttribute("amount", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);
        detailDataModel.addAttribute("rate", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("notes", DetailFieldType.TEXTAREA);
    }

    private SearchComponentInfo createPartnerSearchInfo() {
        PartnerSearchComponent psc = new PartnerSearchComponent();
        return new SearchComponentInfo(psc);
    }

    @Override
    protected void customizeComponents(Map<String, AttributeComponent> name2AttributeComponent,
            final ContractPayment entity) {
        super.customizeComponents(name2AttributeComponent, entity);
        final JTextField tdfRate = (JTextField) name2AttributeComponent.get("rate").getComponent();
        final MoneyComponent mc = (MoneyComponent) name2AttributeComponent.get("amount").getComponent();
        mc.addValueChangedListener(new IValueChangedListener() {
            @Override
            public void doValueChanged(ChangeEvent e) {
                Money money = mc.getMoney();
                String currencyCode = money.getCurrencyCode();
                Double rate = serviceProvider.getService(IConfigService.class).getExchangeRate(currencyCode,
                        entity.getPaymentDate());
                tdfRate.setText(rate.toString());
            }
        });

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ContractPayment entity) {
        refDataModel.putRefDataList(REF_SALES_CONTRACT, getDaoHelper().getDao(SalesContract.class).findAll());
    }
}

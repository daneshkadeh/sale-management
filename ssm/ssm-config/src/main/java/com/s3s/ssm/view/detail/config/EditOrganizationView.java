/*
 * EditOrganizationView
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
package com.s3s.ssm.view.detail.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditOrganizationView extends AbstractSingleEditView<Organization> {
    private static final long serialVersionUID = 1L;
    private static final String CURRENCY_REF_ID = "0";
    private static final String BANK_REF_ID = "1";
    private static final String STALL_REF_ID = "2";
    private static final String REF_PAYMENT_MODE = "3";
    private static final String REF_SELL_ON_CREDIT = "4";

    public EditOrganizationView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Organization entity) {
        // information of organization

        String infoTab = ControlConfigUtils.getString("label.Organization.infoTab");
        String bankTab = ControlConfigUtils.getString("label.Organization.bankTab");
        String generalTab = ControlConfigUtils.getString("label.Organization.generalTab");
        String ruleCodeTab = ControlConfigUtils.getString("label.Organization.ruleCodeTab");
        String numFormatTab = ControlConfigUtils.getString("label.Organization.numFormatTab");
        String warningTab = ControlConfigUtils.getString("label.Organization.warningTab");

        String usdAcctGrp = ControlConfigUtils.getString("label.Organization.usdAcctGrp");
        String vndAcctGrp = ControlConfigUtils.getString("label.Organization.vndAcctGrp");
        String digitAfterCommaGrp = ControlConfigUtils.getString("label.Organization.digitAfterCommaGrp");
        String separatorGrp = ControlConfigUtils.getString("label.Organization.separatorGrp");

        detailDataModel.tab(infoTab, infoTab, null);
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("address", FieldTypeEnum.TEXTAREA).mandatory(true);
        // information of bank
        detailDataModel.tab(bankTab, bankTab, null);
        detailDataModel.addAttribute("beneficeName", FieldTypeEnum.TEXTBOX);
        detailDataModel.startGroup(usdAcctGrp);
        detailDataModel.addAttribute("usdBankAcct.bank", FieldTypeEnum.DROPDOWN).referenceDataId(BANK_REF_ID);
        detailDataModel.addAttribute("usdBankAcct.accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("usdBankAcct.accountNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.endGroup();

        detailDataModel.startGroup(vndAcctGrp);
        detailDataModel.addAttribute("vndBankAcct.bank", FieldTypeEnum.DROPDOWN).referenceDataId(BANK_REF_ID);
        detailDataModel.addAttribute("vndBankAcct.accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("vndBankAcct.accountNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.endGroup();
        // general parameter
        detailDataModel.tab(generalTab, generalTab, null);
        detailDataModel.addAttribute("defCurrency", FieldTypeEnum.DROPDOWN).mandatory(true)
                .referenceDataId(CURRENCY_REF_ID);
        detailDataModel.addAttribute("defPaymentMethod", FieldTypeEnum.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PAYMENT_MODE);
        detailDataModel.addAttribute("defStall", FieldTypeEnum.DROPDOWN).mandatory(true).referenceDataId(STALL_REF_ID);
        detailDataModel.addAttribute("defDetailInvNum", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("defPageRowNum", FieldTypeEnum.TEXTBOX).mandatory(true);
        // rule of code generation
        detailDataModel.tab(ruleCodeTab, ruleCodeTab, null);
        detailDataModel.addAttribute("orderInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("salesInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("salesRefundInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("purInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("purRefundInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("sponContractCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("movementInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("exportInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("importInvCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("paymentBillCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("receiptsCodeRule", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("promotionCodeRule", FieldTypeEnum.TEXTBOX);

        detailDataModel.tab(numFormatTab, numFormatTab, null);
        detailDataModel.startGroup(digitAfterCommaGrp);
        detailDataModel.addAttribute("digitAfterQuan", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("digitAfterUnitPrice", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("digitAfterAmt", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("digitAfterRate", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.endGroup();
        detailDataModel.startGroup(separatorGrp);
        detailDataModel.addAttribute("thousandsSeparator", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("oddSeparator", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.endGroup();

        // sold on credit
        // detailDataModel.tab(warningTab, numFormatTab, null);
        // detailDataModel.addAttribute("sellOnCredit", FieldTypeEnum.RADIO_BUTTON_GROUP);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Organization entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(CURRENCY_REF_ID, getDaoHelper().getDao(SCurrency.class).findAll(), null);
        refDataModel.putRefDataList(BANK_REF_ID, getDaoHelper().getDao(Bank.class).findAll(), null);
        refDataModel.putRefDataList(STALL_REF_ID, getDaoHelper().getDao(Stall.class).findAll(), null);
        refDataModel.putRefDataList(REF_PAYMENT_MODE, Arrays.asList(PaymentMode.values()), null);
        // refDataModel.putRefDataList(REF_SELL_ON_CREDIT, Arrays.asList(PaymentMode.values()), null);
    }

    @Override
    protected Organization loadForCreate() {
        Organization org = super.loadForCreate();
        if (org.getUsdBankAcct() == null) {
            org.setUsdBankAcct(new BankAccount());
        }
        if (org.getVndBankAcct() == null) {
            org.setVndBankAcct(new BankAccount());
        }
        return org;
    }

    @Override
    protected Organization loadForEdit(List<String> eagerLoadedProperties) {
        Organization org = super.loadForEdit(eagerLoadedProperties);
        if (org.getUsdBankAcct() == null) {
            org.setUsdBankAcct(new BankAccount());
        }
        if (org.getVndBankAcct() == null) {
            org.setVndBankAcct(new BankAccount());
        }
        return org;
    }
}

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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

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
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA).mandatory(true);
        detailDataModel.addAttribute("isDefault", DetailFieldType.CHECKBOX);
        // information of bank
        detailDataModel.tab(bankTab, bankTab, null);
        detailDataModel.addAttribute("beneficeName", DetailFieldType.TEXTBOX);
        detailDataModel.startGroup(usdAcctGrp);
        detailDataModel.addAttribute("usdBankAcct.bank", DetailFieldType.DROPDOWN).referenceDataId(BANK_REF_ID);
        detailDataModel.addAttribute("usdBankAcct.accountName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("usdBankAcct.accountNumber", DetailFieldType.TEXTBOX);
        detailDataModel.endGroup();

        detailDataModel.startGroup(vndAcctGrp);
        detailDataModel.addAttribute("vndBankAcct.bank", DetailFieldType.DROPDOWN).referenceDataId(BANK_REF_ID);
        detailDataModel.addAttribute("vndBankAcct.accountName", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("vndBankAcct.accountNumber", DetailFieldType.TEXTBOX);
        detailDataModel.endGroup();
        // general parameter
        detailDataModel.tab(generalTab, generalTab, null);
        detailDataModel.addAttribute("defCurrency", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(CURRENCY_REF_ID);
        detailDataModel.addAttribute("defPaymentMethod", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(REF_PAYMENT_MODE);
        detailDataModel.addAttribute("defStall", DetailFieldType.DROPDOWN).mandatory(true)
                .referenceDataId(STALL_REF_ID);
        detailDataModel.addAttribute("defDetailInvNum", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("defPageRowNum", DetailFieldType.TEXTBOX).mandatory(true);
        // rule of code generation
        detailDataModel.tab(ruleCodeTab, ruleCodeTab, null);
        detailDataModel.addAttribute("orderInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("salesInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("salesRefundInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("purInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("purRefundInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("sponContractCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("movementInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("exportInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("importInvCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("paymentBillCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("receiptsCodeRule", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("promotionCodeRule", DetailFieldType.TEXTBOX);

        detailDataModel.tab(numFormatTab, numFormatTab, null);
        detailDataModel.startGroup(digitAfterCommaGrp);
        detailDataModel.addAttribute("digitAfterQuan", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("digitAfterUnitPrice", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("digitAfterAmt", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("digitAfterRate", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.endGroup();
        detailDataModel.startGroup(separatorGrp);
        detailDataModel.addAttribute("thousandsSeparator", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("oddSeparator", DetailFieldType.TEXTBOX).mandatory(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveOrUpdate(Organization entity) {
        if (entity.getIsDefault() == true) {
            DetachedCriteria dc = getDaoHelper().getDao(getEntityClass()).getCriteria();
            dc.add(Restrictions.eq("isDefault", true));
            Organization org = getDaoHelper().getDao(Organization.class).findFirstByCriteria(dc);
            if (!org.getCode().equals(entity.getCode())) {
                org.setIsDefault(false);
                getDaoHelper().getDao(getEntityClass()).saveOrUpdate(org);
            }
        }
        getDaoHelper().getDao(getEntityClass()).saveOrUpdate(entity);
        this.repaint();
    }
}

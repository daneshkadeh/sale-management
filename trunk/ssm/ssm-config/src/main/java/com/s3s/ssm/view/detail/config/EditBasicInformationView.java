/*
 * EditBasicInformationView
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

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.config.BasicInformation;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.AbstractSingleEditView;

public class EditBasicInformationView extends AbstractSingleEditView<BasicInformation> {
    private static final long serialVersionUID = 1L;
    private static final String CURRENCY_REF_ID = "0";
    private static final String BANK_REF_ID = "1";
    private static final String STALL_REF_ID = "2";

    public EditBasicInformationView(BasicInformation entity) {
        super(entity);
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, BasicInformation entity) {
        // information of company
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("companyName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("agent", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("position", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("logo.data", FieldTypeEnum.IMAGE);
        detailDataModel.addAttribute("companyAddress", FieldTypeEnum.TEXTAREA);
        detailDataModel.addAttribute("tel", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("fax", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("website", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("email", FieldTypeEnum.TEXTBOX);
        // information of bank
        detailDataModel.addAttribute("usdBankAccount.bank", FieldTypeEnum.DROPDOWN).referenceDataId(BANK_REF_ID);
        detailDataModel.addAttribute("usdBankAccount.accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("usdBankAccount.accountNumber", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("vndBankAccount.bank", FieldTypeEnum.DROPDOWN).referenceDataId(BANK_REF_ID);
        detailDataModel.addAttribute("vndBankAccount.accountName", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("vndBankAccount.accountNumber", FieldTypeEnum.TEXTBOX);
        // general parameter
        detailDataModel.addAttribute("defCurrency", FieldTypeEnum.DROPDOWN).referenceDataId(CURRENCY_REF_ID);
        detailDataModel.addAttribute("defDetailInvNum", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("defPageRowNum", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("defPaymentMethod", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("defStall", FieldTypeEnum.DROPDOWN).referenceDataId(STALL_REF_ID);
        // rule of code generation
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
        // the path backup data
        detailDataModel.addAttribute("backupPath", FieldTypeEnum.FILE_CHOOSER);
        // sold on credit
        detailDataModel.addAttribute("digitAfterCommaQuan", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("digitAfterCommaPrice", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("digitAfterCommaRate", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("thousandsSeparator", FieldTypeEnum.TEXTBOX);
        detailDataModel.addAttribute("oddSeparator", FieldTypeEnum.TEXTBOX);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, BasicInformation entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(CURRENCY_REF_ID, getDaoHelper().getDao(SCurrency.class).findAll(), null);
        refDataModel.putRefDataList(BANK_REF_ID, getDaoHelper().getDao(Bank.class).findAll(), null);
        refDataModel.putRefDataList(STALL_REF_ID, getDaoHelper().getDao(Stall.class).findAll(), null);
    }

    @Override
    protected void loadForCreate(BasicInformation entity) {
        super.loadForCreate(entity);
        entity.setLogo(new UploadFile());
    }

    @Override
    protected void loadForEdit(BasicInformation entity) {
        super.loadForEdit(entity);
        if (entity.getLogo() == null) {
            entity.setLogo(new UploadFile());
        }
        if (entity.getUsdBankAccount() == null) {
            entity.setUsdBankAccount(new BankAccount());
        }
        if (entity.getVndBankAccount() == null) {
            entity.setVndBankAccount(new BankAccount());
        }
    }

    @Override
    protected void saveOrUpdate(BasicInformation entity) {
        // Save Image. TODO: check image changed or not before saving.
        entity.getLogo().setTitle(entity.getCode());
        getDaoHelper().getDao(UploadFile.class).saveOrUpdate(entity.getLogo());
        super.saveOrUpdate(entity);
    }
}

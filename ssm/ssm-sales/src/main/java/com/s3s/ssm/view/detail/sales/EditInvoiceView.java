/*
 * EditInvoiceView
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
package com.s3s.ssm.view.detail.sales;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.contact.Customer;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.utils.InvoiceHelper;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditInvoiceView extends AbstractMasterDetailView<Invoice, DetailInvoice> {

    private static final String REF_TYPE = "type";
    private static final String REF_CONTACT = "contact";
    private static final String REF_STATUS = "status";
    private static final String REF_PAY_STATUS = "paymentStatus";
    private static final String REF_CURRENCY = "currency";

    public EditInvoiceView(Invoice entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        listDataModel.add(new DetailAttribute("item", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("packageLine", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("amount", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("priceAfterTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("type", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("status", FieldTypeEnum.TEXTBOX));
    }

    @Override
    protected Class<? extends AbstractEditView<DetailInvoice>> getChildDetailViewClass() {
        return EditDetailInvoiceVirtualView.class;
    }

    @Override
    protected String getChildFieldName() {
        return "listDetailInvoices";
    }

    @Override
    protected void loadForCreate(Invoice entity) {
        super.loadForCreate(entity);
        entity.setCreatedDate(new Date());
        entity.setInvoiceNumber(InvoiceHelper.getNextInvoiceNumber());
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Invoice entity) {
        detailDataModel.addAttribute("invoiceNumber", FieldTypeEnum.TEXTBOX).editable(false);
        detailDataModel.addAttribute("type", FieldTypeEnum.DROPDOWN).referenceDataId(REF_TYPE);
        detailDataModel.addAttribute("createdDate", FieldTypeEnum.DATE);

        // TODO: contact will be chosen from and listSearchView
        detailDataModel.addAttribute("contact", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CONTACT);
        detailDataModel.addAttribute("moneyAfterTax", FieldTypeEnum.TEXTBOX);

        // TODO: how to identify currency for an invoice, if 1 item is USD, 1 item is VND
        // detailDataModel.addAttribute("currency", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CURRENCY);
        detailDataModel.addAttribute("status", FieldTypeEnum.DROPDOWN).referenceDataId(REF_STATUS);
        detailDataModel.addAttribute("paymentStatus", FieldTypeEnum.DROPDOWN).referenceDataId(REF_PAY_STATUS);

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Invoice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_CONTACT, getDaoHelper().getDao(Customer.class).findAll(), null);
        // refDataModel.putRefDataList(REF_CURRENCY, getDaoHelper().getDao(SCurrency.class).findAll(), null);
        refDataModel.putRefDataList(REF_STATUS, InvoiceStatus.values());
        refDataModel.putRefDataList(REF_TYPE, InvoiceType.values());
        refDataModel.putRefDataList(REF_PAY_STATUS, InvoicePaymentStatus.values());
    }

    @Override
    protected void addDetailIntoMaster(Invoice masterEntity, DetailInvoice detailEntity) {
        masterEntity.addDetailInvoice(detailEntity);
    }

    @Override
    protected void saveOrUpdate(Invoice masterEntity, List<DetailInvoice> detailEntities) {
        masterEntity.setMoneyBeforeTax(masterEntity.getMoneyAfterTax());
        super.saveOrUpdate(masterEntity, detailEntities);
    }
}

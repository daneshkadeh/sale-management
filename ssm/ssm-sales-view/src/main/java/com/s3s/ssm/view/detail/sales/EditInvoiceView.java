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
import java.util.Map;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class EditInvoiceView extends AbstractMasterDetailView<Invoice, DetailInvoice> {

    private static final String REF_TYPE = "type";
    private static final String REF_CONTACT = "contact";
    private static final String REF_STATUS = "status";
    private static final String REF_PAY_STATUS = "paymentStatus";
    private static final String REF_CURRENCY = "currency";

    public EditInvoiceView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("item", ListColumnType.TEXT);
        listDataModel.addColumn("packageLine", ListColumnType.TEXT);
        listDataModel.addColumn("amount", ListColumnType.TEXT);
        listDataModel.addColumn("priceAfterTax", ListColumnType.TEXT);
        listDataModel.addColumn("moneyAfterTax", ListColumnType.TEXT);
        listDataModel.addColumn("type", ListColumnType.TEXT);
        listDataModel.addColumn("status", ListColumnType.TEXT);
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
    protected Invoice loadForCreate() {
        Invoice invoice = super.loadForCreate();
        invoice.setCreatedDate(new Date());
        invoice.setInvoiceNumber(serviceProvider.getService(InvoiceService.class).getNextInvoiceNumber());
        return invoice;
    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, Invoice entity) {
        detailDataModel.addAttribute("invoiceNumber", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_TYPE);
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);

        // TODO: contact will be chosen from and listSearchView
        detailDataModel.addAttribute("contact", DetailFieldType.DROPDOWN).referenceDataId(REF_CONTACT);
        detailDataModel.addAttribute("moneyAfterTax", DetailFieldType.TEXTBOX);

        // TODO: how to identify currency for an invoice, if 1 item is USD, 1 item is VND
        // detailDataModel.addAttribute("currency", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CURRENCY);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_STATUS);
        detailDataModel.addAttribute("paymentStatus", DetailFieldType.DROPDOWN).referenceDataId(REF_PAY_STATUS);

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Invoice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_CONTACT, getDaoHelper().getDao(Partner.class).findAll(), null);
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

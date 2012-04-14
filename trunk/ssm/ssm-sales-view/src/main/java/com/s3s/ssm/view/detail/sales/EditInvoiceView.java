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
import java.util.Map;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailInvoiceStatus;
import com.s3s.ssm.entity.sales.DetailInvoiceType;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class EditInvoiceView extends AbstractMasterDetailView<Invoice, DetailInvoice> {

    private static final String REF_CURRENCY = "REF_CURRENCY";
    private static final String REF_ITEM = "item";
    private static final String REF_PACKLINE = "packageLine";
    private static final String REF_INVOICE_TYPE = "type";
    private static final String REF_D_INVOICE_TYPE = "detailtype";
    private static final String REF_CONTACT = "contact";
    private static final String REF_INVOICE_STATUS = "status";
    private static final String REF_D_INVOICE_STATUS = "detailstatus";
    private static final String REF_PAY_STATUS = "paymentStatus";

    public EditInvoiceView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.setEditable(true);
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_ITEM)
                .width(150, 50, 200);
        listDataModel.addColumn("packageLine", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_PACKLINE);
        listDataModel.addColumn("amount", ListRendererType.TEXT);
        listDataModel.addColumn("priceAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("moneyAfterTax", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(120);
        listDataModel.addColumn("type", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_TYPE);
        listDataModel.addColumn("status", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_D_INVOICE_STATUS);
        listDataModel.addColumn("totalAmount", ListRendererType.TEXT).notEditable();
    }

    @Override
    protected Class<? extends AbstractEditView<DetailInvoice>> getChildDetailViewClass() {
        return EditDetailInvoiceVirtualView.class;
    }

    @Override
    protected String getParentFieldName() {
        return "invoice";
    }

    @Override
    protected String getChildFieldName() {
        return "detailInvoices";
    }

    @Override
    protected Invoice loadForCreate(Map<String, Object> request) {
        Invoice invoice = super.loadForCreate(request);
        invoice.setCreatedDate(new Date());
        invoice.setInvoiceNumber(serviceProvider.getService(InvoiceService.class).getNextInvoiceNumber());
        return invoice;
    }

    @Override
    protected void
            initialPresentationView(DetailDataModel detailDataModel, Invoice entity, Map<String, Object> request) {
        detailDataModel.addAttribute("invoiceNumber", DetailFieldType.TEXTBOX).editable(false);
        detailDataModel.addAttribute("type", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_TYPE);
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);

        // TODO: contact will be chosen from and listSearchView
        detailDataModel.addAttribute("contact", DetailFieldType.DROPDOWN).referenceDataId(REF_CONTACT);
        detailDataModel.addAttribute("moneyAfterTax", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY);

        // TODO: how to identify currency for an invoice, if 1 item is USD, 1 item is VND
        // detailDataModel.addAttribute("currency", FieldTypeEnum.DROPDOWN).referenceDataId(REF_CURRENCY);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN).referenceDataId(REF_INVOICE_STATUS);
        detailDataModel.addAttribute("paymentStatus", DetailFieldType.DROPDOWN).referenceDataId(REF_PAY_STATUS);

    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, Invoice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_CONTACT, getDaoHelper().getDao(Partner.class).findAll());
        refDataModel.putRefDataList(REF_INVOICE_STATUS, InvoiceStatus.values());
        refDataModel.putRefDataList(REF_INVOICE_TYPE, InvoiceType.values());
        refDataModel.putRefDataList(REF_D_INVOICE_STATUS, DetailInvoiceStatus.values());
        refDataModel.putRefDataList(REF_D_INVOICE_TYPE, DetailInvoiceType.values());
        refDataModel.putRefDataList(REF_PAY_STATUS, InvoicePaymentStatus.values());
        refDataModel.putRefDataList(REF_ITEM, getDaoHelper().getDao(Item.class).findAll());
        refDataModel.putRefDataList(REF_PACKLINE, getDaoHelper().getDao(PackageLine.class).findAll());
        refDataModel.putRefDataList(REF_CURRENCY, serviceProvider.getService(IConfigService.class).getCurrencyCodes());
    }

    // @Override
    // protected void addDetailIntoMaster(Invoice masterEntity, DetailInvoice detailEntity) {
    // masterEntity.addDetailInvoice(detailEntity);
    // }

    @Override
    protected void saveOrUpdate(Invoice masterEntity) {
        masterEntity.setMoneyBeforeTax(masterEntity.getMoneyAfterTax());
        super.saveOrUpdate(masterEntity);
    }

}

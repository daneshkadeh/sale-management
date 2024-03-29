/*
 * EditExportStoreFormView
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
package com.s3s.ssm.view.detail.store;

import java.util.Map;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.Invoice.InvoiceStoreStatus;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.sales.InvoiceService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.ListComponentInfo;
import com.s3s.ssm.view.list.store.ListExportStoreFormView;
import com.s3s.ssm.view.util.StoreViewHelper;

public class EditExportStoreFormView extends AbstractSingleEditView<ExportStoreForm> {
    private static final long serialVersionUID = -7472571972492175768L;
    private static String REF_INVOICE_LIST = "0";
    // TODO:Hoang remove bellow after ListDataModel support caching
    private static String REF_UNIT_UOM = "1";
    private static String REF_LIST_PRODUCT = "2";
    private static String REF_LIST_ITEM = "3";

    /**
     * @param entity
     */
    public EditExportStoreFormView(Map<String, Object> entity) {
        super(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ExportStoreForm loadForCreate(Map<String, Object> request) {
        ExportStoreForm form = super.loadForCreate(request);
        Invoice invoice = (Invoice) request.get(ListExportStoreFormView.INVOICE_FORM);
        StoreViewHelper.initExportStoreForm(form, invoice);
        return form;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void preSaveOrUpdate(ExportStoreForm masterEntity) {
        super.preSaveOrUpdate(masterEntity);
        // set quantity
        long reqQuanTotal = 0;
        long realQuanTotal = 0;
        long remainQuanTotal = 0;
        for (DetailExportStore detail : masterEntity.getExportDetails()) {
            reqQuanTotal += detail.getReqQuan();
            realQuanTotal += detail.getRealQuan();
            remainQuanTotal += detail.getRemainQuan();
        }
        masterEntity.setReqQuanTotal(reqQuanTotal);
        masterEntity.setRealQuanTotal(realQuanTotal);
        masterEntity.setRemainQuanTotal(remainQuanTotal);
        // setting status of the invoice
        Invoice invoice = masterEntity.getInvoice();
        boolean isCompleted = true;
        for (DetailExportStore detail : masterEntity.getExportDetails()) {
            if (detail.getRemainQuan() > 0) {
                isCompleted = false;
            }
        }
        if (isCompleted) {
            invoice.setStoreStatus(InvoiceStoreStatus.EXPORTED);
        } else {
            invoice.setStoreStatus(InvoiceStoreStatus.EXPORTING);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ExportStoreForm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_EXPORT_STORE_STATUS).newColumn();
        // detailDataModel.addAttribute("printAfterSave", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("transType", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_TRANS_TYPE)
                .newColumn();
        detailDataModel.addAttribute("store", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE)
                .mandatory(true);
        detailDataModel.addAttribute("transPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY)
                .newColumn();
        detailDataModel.addAttribute("staff", DetailFieldType.SEARCHER).mandatory(true)
                .componentInfo(ComponentFactory.createStorekeeperComponentInfo());
        detailDataModel.addAttribute("invoice", DetailFieldType.SEARCHER).componentInfo(
                ComponentFactory.createSalesInvoiceComponentInfo());
        detailDataModel.addAttribute("exportDetails", DetailFieldType.LIST).componentInfo(
                createExportDetailsComponentInfo());
    }

    private IComponentInfo createExportDetailsComponentInfo() {
        ListExportDetailComponent component = new ListExportDetailComponent(null, null, null);
        return new ListComponentInfo(component, "exportForm");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ExportStoreForm entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_INVOICE_LIST, serviceProvider.getService(InvoiceService.class).getAllInvoice());
        refDataModel.putRefDataList(REF_UNIT_UOM, serviceProvider.getService(IConfigService.class).getUnitUom());
        refDataModel.putRefDataList(REF_LIST_PRODUCT, serviceProvider.getService(ICatalogService.class)
                .getListProducts());
        refDataModel.putRefDataList(REF_LIST_ITEM, serviceProvider.getService(ICatalogService.class).getAllItem());
    }
}

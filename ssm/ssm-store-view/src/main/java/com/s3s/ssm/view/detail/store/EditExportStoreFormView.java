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

import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
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

public class EditExportStoreFormView extends AbstractMasterDetailView<ExportStoreForm, DetailExportStore> {
    private static final long serialVersionUID = -7472571972492175768L;
    private static String REF_INVOICE_LIST = "0";
    // TODO:Hoang remove bellow after ListDataModel support caching
    private static String REF_UNIT_UOM = "1";
    private static String REF_LIST_PRODUCT = "2";

    /**
     * @param entity
     */
    public EditExportStoreFormView(Map<String, Object> entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        // TODO: Hoang must set max, min for column
        listDataModel.setEditable(true);
        listDataModel.addColumn("lineNo", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(
                REF_LIST_PRODUCT);
        listDataModel.addColumn("product.name", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("uom", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_UNIT_UOM);
        listDataModel.addColumn("baseUom", ListRendererType.TEXT, ListEditorType.TEXTFIELD).notEditable();
        listDataModel.addColumn("reqQuan", ListRendererType.NUMBER).summarized();
        listDataModel.addColumn("realQuan", ListRendererType.NUMBER).summarized();
        listDataModel.addColumn("remainQuan", ListRendererType.NUMBER).summarized();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<DetailExportStore>> getChildDetailViewClass() {
        return EditDetailExportStoreView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getChildFieldName() {
        return "exportDetails";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addDetailIntoMaster(ExportStoreForm masterEntity, DetailExportStore detailEntity) {
        masterEntity.addDetailImports(detailEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ExportStoreForm entity) {
        super.initialPresentationView(detailDataModel, entity);
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("printAfterSave", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_EXPORT_STORE_STATUS).newColumn();

        detailDataModel.addAttribute("store", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE)
                .mandatory(true);
        detailDataModel.addAttribute("transType", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_TRANS_TYPE)
                .newColumn();
        detailDataModel.addAttribute("invoice", DetailFieldType.ENTITY_CHOOSER).referenceDataId(REF_INVOICE_LIST)
                .mandatory(true);
        detailDataModel.addAttribute("transPrice", DetailFieldType.MONEY).cacheDataId(CacheId.REF_LIST_CURRENCY)
                .newColumn();
        detailDataModel.addAttribute("custCode", DetailFieldType.LABEL);
        detailDataModel.addAttribute("custName", DetailFieldType.LABEL);
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
    }

}

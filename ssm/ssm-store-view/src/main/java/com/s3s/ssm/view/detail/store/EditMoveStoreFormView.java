/*
 * EditExchangeStoreFormView
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

import com.s3s.ssm.entity.store.DetailMoveStore;
import com.s3s.ssm.entity.store.MoveStoreForm;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class EditMoveStoreFormView extends AbstractMasterDetailView<MoveStoreForm, DetailMoveStore> {
    // TODO:Hoang remove bellow after ListDataModel support caching
    private static String REF_UNIT_UOM = "1";
    private static String REF_LIST_PRODUCT = "2";
    private static String REF_LIST_ITEM = "3";

    public EditMoveStoreFormView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        listDataModel.setEditable(true);
        // listDataModel.addColumn("lineNo", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.COMBOBOX)
                .referenceDataId(REF_LIST_PRODUCT).width(180);
        listDataModel.addColumn("product.name", ListRendererType.TEXT).notEditable().width(290);
        // TODO: Hoang the data should be updated after choosing the product
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_LIST_ITEM)
                .width(205);
        listDataModel.addColumn("uom", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_UNIT_UOM)
                .width(70);
        listDataModel.addColumn("exportQty", ListRendererType.NUMBER, ListEditorType.TEXTFIELD)
                .width(UIConstants.QTY_COLUMN_WIDTH).summarized();
        listDataModel.addColumn("importQty", ListRendererType.NUMBER, ListEditorType.TEXTFIELD)
                .width(UIConstants.QTY_COLUMN_WIDTH).summarized();

    }

    @Override
    protected Class<? extends AbstractEditView<DetailMoveStore>> getChildDetailViewClass() {
        return null;
    }

    @Override
    protected String getParentFieldName() {
        return "moveForm";
    }

    @Override
    protected String getChildFieldName() {
        return "detailSet";
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, MoveStoreForm entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("status", DetailFieldType.DROPDOWN)
                .cacheDataId(CacheId.REF_LIST_MOVE_STORE_STATUS).newColumn();
        detailDataModel.addAttribute("createdDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("transType", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_TRANS_TYPE)
                .newColumn();
        detailDataModel.addAttribute("staff", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_OPERATOR);
        detailDataModel.addAttribute("transporter", DetailFieldType.TEXTBOX).newColumn();
        detailDataModel.addAttribute("sentDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("fromStore", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE)
                .newColumn();
        detailDataModel.addAttribute("receivedDate", DetailFieldType.DATE);
        detailDataModel.addAttribute("destStore", DetailFieldType.DROPDOWN).cacheDataId(CacheId.REF_LIST_STORE)
                .newColumn();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, MoveStoreForm entity) {
        super.setReferenceDataModel(refDataModel, entity);
        refDataModel.putRefDataList(REF_UNIT_UOM, serviceProvider.getService(IConfigService.class).getUnitUom());
        refDataModel.putRefDataList(REF_LIST_PRODUCT, serviceProvider.getService(ICatalogService.class)
                .getListProducts());
        refDataModel.putRefDataList(REF_LIST_ITEM, serviceProvider.getService(ICatalogService.class).getAllItem());
    }

    @Override
    protected String getDefaultTitle(MoveStoreForm entity) {
        return ControlConfigUtils.getString("label.MoveStoreForm.detail.title") + UIConstants.BLANK + entity.getCode();
    }
}
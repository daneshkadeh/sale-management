/*
 * ListExportDetailComponent
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

import javax.swing.Icon;

import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ServiceProvider;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Phan Hong Phuc
 * @since Apr 22, 2012
 */
public class ListExportDetailComponent extends AListComponent<DetailExportStore> {
    private static final long serialVersionUID = 4080249415619611818L;
    private static final String REF_LIST_PRODUCT = "1";
    private static final String REF_LIST_ITEM = "2";
    private static final String REF_UNIT_UOM = "3";

    /**
     * @param entities
     * @param icon
     * @param label
     * @param tooltip
     */
    public ListExportDetailComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        // TODO: Hoang must set max, min for column
        // listDataModel.setEditable(true);
        // listDataModel.addColumn("lineNo", ListRendererType.TEXT).notEditable();
        listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.COMBOBOX)
                .referenceDataId(REF_LIST_PRODUCT).width(180);
        listDataModel.addColumn("productName", ListRendererType.TEXT).notEditable().width(290);
        // TODO: Hoang the data should be updated after choosing the product
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_LIST_ITEM)
                .width(205);
        listDataModel.addColumn("uom", ListRendererType.TEXT, ListEditorType.COMBOBOX).referenceDataId(REF_UNIT_UOM)
                .width(70);
        listDataModel.addColumn("baseUom", ListRendererType.TEXT, ListEditorType.TEXTFIELD).notEditable().width(70);
        listDataModel.addColumn("reqQuan", ListRendererType.NUMBER).notEditable().summarized()
                .width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("realQuan", ListRendererType.NUMBER).summarized().width(UIConstants.QTY_COLUMN_WIDTH);
        listDataModel.addColumn("remainQuan", ListRendererType.NUMBER).notEditable().summarized()
                .width(UIConstants.QTY_COLUMN_WIDTH);

    }

    @Override
    protected ReferenceDataModel initReferenceDataModel() {
        ReferenceDataModel refDataModel = super.initReferenceDataModel();
        ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();
        refDataModel.putRefDataList(REF_UNIT_UOM, serviceProvider.getService(IConfigService.class).getUnitUom());
        refDataModel.putRefDataList(REF_LIST_PRODUCT, serviceProvider.getService(ICatalogService.class)
                .getListProducts());
        refDataModel.putRefDataList(REF_LIST_ITEM, serviceProvider.getService(ICatalogService.class).getAllItem());
        return refDataModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DetailExportStore createNewEntity() {
        return super.createNewEntity();
    }

}

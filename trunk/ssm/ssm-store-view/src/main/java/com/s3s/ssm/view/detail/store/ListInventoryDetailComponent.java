/*
 * ListImportDetailComponent
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.detail.store;

import java.util.List;

import javax.swing.Icon;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.store.DetailInventoryStore;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ServiceProvider;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListInventoryDetailComponent extends AListComponent<DetailInventoryStore> {
    private static final long serialVersionUID = -7918793477000623379L;
    // TODO:Hoang remove bellow after ListDataModel support caching
    private static String REF_LIST_PRODUCT = "2";
    private static String REF_LIST_ITEM = "3";

    /**
     * @param icon
     * @param label
     * @param tooltip
     */
    public ListInventoryDetailComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        // TODO: Hoang must set max, min for column
        // listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.COMBOBOX)
        // .referenceDataId(REF_LIST_PRODUCT).width(180);
        // listDataModel.addColumn("product.name", ListRendererType.TEXT).notEditable().width(290);
        // // TODO: Hoang the data should be updated after choosing the product
        // listDataModel.addColumn("item", ListRendererType.TEXT,
        // ListEditorType.COMBOBOX).referenceDataId(REF_LIST_ITEM)
        // .width(205);
        // listDataModel.addColumn("baseUom", ListRendererType.TEXT);
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.SEARCH_COMPONENT)
                .componentInfo(ComponentFactory.createItemComponentInfo()).width(UIConstants.PRODUCT_CODE_COLUMN_WIDTH);
        listDataModel.addColumn("item.sumUomName", ListRendererType.TEXT).notEditable()
                .width(UIConstants.PRODUCT_NAME_COLUMN_WIDTH);
        listDataModel.addColumn("item.uom", ListRendererType.TEXT).notEditable().width(UIConstants.UOM_COLUMN_WIDTH);
        listDataModel.addColumn("priceUnit", ListRendererType.NUMBER).notEditable().width(UIConstants.AMT_COLUMN_WIDTH);
        listDataModel.addColumn("curQty", ListRendererType.NUMBER).notEditable().width(UIConstants.QTY_COLUMN_WIDTH)
                .summarized();
        listDataModel.addColumn("curPriceSubtotal", ListRendererType.NUMBER).notEditable()
                .width(UIConstants.AMT_COLUMN_WIDTH).summarized();
        listDataModel.addColumn("realQty", ListRendererType.NUMBER).width(UIConstants.QTY_COLUMN_WIDTH).summarized();
        listDataModel.addColumn("realPriceSubtotal", ListRendererType.NUMBER).notEditable()
                .width(UIConstants.AMT_COLUMN_WIDTH).summarized();
        listDataModel.addColumn("lostQty", ListRendererType.NUMBER).notEditable().width(UIConstants.QTY_COLUMN_WIDTH)
                .summarized();

    }

    @Override
    protected ReferenceDataModel initReferenceDataModel() {
        ReferenceDataModel refDataModel = super.initReferenceDataModel();
        ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();
        refDataModel.putRefDataList(REF_LIST_PRODUCT, serviceProvider.getService(ICatalogService.class)
                .getListProducts());
        // TODO: Hoang update item after selecting product
        refDataModel.putRefDataList(REF_LIST_ITEM, serviceProvider.getService(ICatalogService.class).getAllItem());
        return refDataModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRowUpdated(String attributeName, DetailInventoryStore entityUpdated,
            List<DetailInventoryStore> entities) {
        super.doRowUpdated(attributeName, entityUpdated, entities);
        if ("realQty".equals(attributeName)) {
            Item item = entityUpdated.getItem();
            if (item != null) {
                Integer curQty = entityUpdated.getCurQty();
                Integer realQty = entityUpdated.getRealQty();
                Integer lostQty = curQty - realQty;
                Money priceUnit = item.getOriginPrice();
                Money realPriceSubtotal = priceUnit.multiply(realQty);
                entityUpdated.setLostQty(lostQty);
                entityUpdated.setRealPriceSubtotal(realPriceSubtotal);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isInsertRowAllowed() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isDeleteRowAllowed() {
        return false;
    }

}

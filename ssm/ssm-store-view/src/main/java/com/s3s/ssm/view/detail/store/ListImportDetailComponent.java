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
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.interfaces.config.IConfigService;
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
public class ListImportDetailComponent extends AListComponent<DetailImportStore> {
    private static final long serialVersionUID = 9143672291866681219L;
    // TODO:Hoang remove bellow after ListDataModel support caching
    private static final String REF_CURRENCY = "REF_CURRENCY";

    /**
     * @param icon
     * @param label
     * @param tooltip
     */
    public ListImportDetailComponent(Icon icon, String label, String tooltip) {
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
        // listDataModel.addColumn("product", ListRendererType.TEXT, ListEditorType.SEARCH_COMPONENT)
        // .componentInfo(ComponentFactory.createProductComponentInfo()).width(180);
        // listDataModel.addColumn("productName", ListRendererType.TEXT).notEditable().width(290);
        // TODO: Hoang the data should be updated after choosing the product
        listDataModel.addColumn("item", ListRendererType.TEXT, ListEditorType.SEARCH_COMPONENT)
                .componentInfo(ComponentFactory.createItemComponentInfo()).width(UIConstants.PRODUCT_CODE_COLUMN_WIDTH);
        listDataModel.addColumn("item.sumUomName", ListRendererType.TEXT).notEditable()
                .width(UIConstants.PRODUCT_NAME_COLUMN_WIDTH);
        listDataModel.addColumn("item.uom", ListRendererType.TEXT).notEditable().width(UIConstants.UOM_COLUMN_WIDTH);
        // listDataModel.addColumn("baseUom", ListRendererType.TEXT, ListEditorType.TEXTFIELD).notEditable();
        listDataModel.addColumn("quantity", ListRendererType.NUMBER, ListEditorType.TEXTFIELD)
                .width(UIConstants.QTY_COLUMN_WIDTH).summarized();
        listDataModel.addColumn("priceUnit", ListRendererType.TEXT, ListEditorType.MONEY)
                .width(UIConstants.AMT_COLUMN_WIDTH).referenceDataId(REF_CURRENCY).notEditable();
        listDataModel.addColumn("priceSubtotal", ListRendererType.TEXT, ListEditorType.MONEY)
                .referenceDataId(REF_CURRENCY).width(UIConstants.AMT_COLUMN_WIDTH).summarized();

    }

    @Override
    protected ReferenceDataModel initReferenceDataModel() {
        ReferenceDataModel refDataModel = super.initReferenceDataModel();
        ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();
        refDataModel.putRefDataList(REF_CURRENCY, serviceProvider.getService(IConfigService.class).getCurrencyCodes());
        return refDataModel;
    }

    @Override
    protected void doRowUpdated(String attributeName, DetailImportStore entity, List<DetailImportStore> entities) {
        super.doRowUpdated(attributeName, entity, entities);
        if ("item".equals(attributeName)) {
            Item item = entity.getItem();
            Product product = item.getProduct();
            UnitOfMeasure uom = item.getUom();
            Money priceUnit = item.getOriginPrice();
            entity.setProduct(product);
            entity.setUom(uom);
            entity.setPriceUnit(priceUnit);
        }
        if ("quantity".equals(attributeName)) {
            Item item = entity.getItem();
            if (item != null) {
                Integer qty = entity.getQuantity();
                Money priceUnit = item.getOriginPrice();
                Money priceSubtotal = priceUnit.multiply(qty);
                entity.setPriceSubtotal(priceSubtotal);
            }
        }
    }

}

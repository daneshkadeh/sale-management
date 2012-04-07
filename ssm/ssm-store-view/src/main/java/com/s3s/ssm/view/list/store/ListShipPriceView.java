/*
 * ListShipDatePriceView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Berg�re 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.list.store;

import javax.swing.SortOrder;

import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.view.detail.store.EditShipPriceView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListShipPriceView extends AbstractListView<ShipPrice> {
    private static final long serialVersionUID = -776291159859019764L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("shipPriceType", ListRendererType.TEXT);
        listDataModel.addColumn("updateDate", ListRendererType.DATE).sort(SortOrder.ASCENDING, 0);
        listDataModel.addColumn("price", ListRendererType.TEXT).sort(SortOrder.ASCENDING, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<ShipPrice>> getEditViewClass() {
        return EditShipPriceView.class;
    }

}

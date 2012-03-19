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

package com.s3s.ssm.view.list.shipment;

import com.s3s.ssm.entity.shipment.ShipDatePrice;
import com.s3s.ssm.view.detail.shipment.EditShipDatePriceDetail;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListShipDatePriceView extends AbstractListView<ShipDatePrice> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("shipPrice", ListColumnType.TEXT);
        listDataModel.addColumn("updateDate", ListColumnType.DATE);
        listDataModel.addColumn("price", ListColumnType.TEXT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<ShipDatePrice>> getEditViewClass() {
        return EditShipDatePriceDetail.class;
    }

}

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

import java.util.List;

import com.s3s.ssm.entity.shipment.ShipDatePrice;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.shipment.EditShipDatePriceDetail;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListShipDatePriceView extends AbstractListView<ShipDatePrice> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("shipPrice", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("updateDate", FieldTypeEnum.DATE));
        listDataModel.add(new DetailAttribute("price", FieldTypeEnum.TEXTBOX));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<ShipDatePrice>> getEditViewClass() {
        return EditShipDatePriceDetail.class;
    }

}
/*
 * EditShipDatePriceDetail
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

package com.s3s.ssm.view.detail.shipment;

import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;

import com.s3s.ssm.entity.shipment.ShipDatePrice;
import com.s3s.ssm.entity.shipment.ShipPrice;
import com.s3s.ssm.model.ReferenceDataModel;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditShipDatePriceDetail extends AbstractSingleEditView<ShipDatePrice> {
    private static final String SHIPPRICE_REF_ID = "1";

    /**
     * @param entity
     */
    public EditShipDatePriceDetail(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ShipDatePrice entity) {
        detailDataModel.addAttribute("shipPrice", DetailFieldType.DROPDOWN).referenceDataId(SHIPPRICE_REF_ID);
        detailDataModel.addAttribute("updateDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("price", DetailFieldType.TEXTBOX).mandatory(true);
    }

    @Override
    protected void setReferenceDataModel(ReferenceDataModel refDataModel, ShipDatePrice entity) {
        super.setReferenceDataModel(refDataModel, entity);
        List<ShipPrice> shipPriceList = getDaoHelper().getDao(ShipPrice.class).findAll();
        refDataModel.putRefDataList(SHIPPRICE_REF_ID, refDataModel.new ReferenceData(shipPriceList,
                new DefaultListCellRenderer()));
    }
}

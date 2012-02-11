/*
 * EditShipmentPrice
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

import com.s3s.ssm.entity.shipment.ShipPrice;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractSingleEditView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditShipPriceView extends AbstractSingleEditView<ShipPrice> {

    /**
     * @param entity
     */
    public EditShipPriceView(ShipPrice entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, ShipPrice entity) {
        detailDataModel.addAttribute("code", FieldTypeEnum.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("name", FieldTypeEnum.TEXTBOX).mandatory(true);
    }

}

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

package com.s3s.ssm.view.detail.store;

import java.util.Map;

import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.util.CacheId;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditShipPriceView extends AbstractSingleEditView<ShipPrice> {
    private static final long serialVersionUID = -7524462966557044478L;

    /**
     * @param entity
     */
    public EditShipPriceView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, ShipPrice entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("updateDate", DetailFieldType.DATE).mandatory(true);
        detailDataModel.addAttribute("shipPriceType", DetailFieldType.DROPDOWN).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_SHIP_PRICE_TYPE);
        detailDataModel.addAttribute("price", DetailFieldType.MONEY).mandatory(true)
                .cacheDataId(CacheId.REF_LIST_CURRENCY);
    }

    @Override
    protected String getDefaultTitle(ShipPrice entity) {
        return ControlConfigUtils.getString("label.ShipPrice.detail.title") + UIConstants.HYPHEN
                + entity.getShipPriceType().getName();
    }
}

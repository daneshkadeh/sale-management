/*
 * EditTransportationTypeView
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

package com.s3s.ssm.view.detail.shipment;

import java.util.Map;

import com.s3s.ssm.entity.shipment.TransportationType;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class EditTransportationTypeView extends AbstractSingleEditView<TransportationType> {
    /**
     * @param entity
     */
    public EditTransportationTypeView(Map<String, Object> entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, TransportationType entity,
            Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("description", DetailFieldType.TEXTAREA);
        detailDataModel.addAttribute("active", DetailFieldType.CHECKBOX);
    }

    @Override
    protected String getDefaultTitle(TransportationType entity) {
        return ControlConfigUtils.getString("label.TransportationType.detail.title") + UIConstants.BLANK
                + entity.getCode();
    }
}

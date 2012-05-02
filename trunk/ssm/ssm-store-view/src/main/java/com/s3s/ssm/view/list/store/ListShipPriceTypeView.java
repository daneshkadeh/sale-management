/*
 * ListShipmentPriceView
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

import javax.swing.Icon;

import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.view.detail.store.EditShipPriceTypeView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.ANonSearchListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListShipPriceTypeView extends ANonSearchListEntityView<ShipPriceType> {

    private static final long serialVersionUID = 3726806486848950939L;

    public ListShipPriceTypeView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<ShipPriceType>> getEditViewClass() {
        return EditShipPriceTypeView.class;
    }

}

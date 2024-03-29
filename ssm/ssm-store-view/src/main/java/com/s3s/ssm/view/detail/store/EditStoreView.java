/*
 * EditStoreView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
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

import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.component.ComponentFactory;
import com.s3s.ssm.view.edit.AbstractSingleEditView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class EditStoreView extends AbstractSingleEditView<Store> {
    private static final long serialVersionUID = 6320968219003700535L;

    public EditStoreView(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected void initialPresentationView(DetailDataModel detailDataModel, Store entity, Map<String, Object> request) {
        detailDataModel.addAttribute("code", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("active", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("name", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("isIntermediate", DetailFieldType.CHECKBOX).newColumn();
        detailDataModel.addAttribute("manager", DetailFieldType.SEARCHER)
                .componentInfo(ComponentFactory.createStorekeeperComponentInfo()).mandatory(true);
        detailDataModel.addAttribute("phone", DetailFieldType.TEXTBOX).mandatory(true);
        detailDataModel.addAttribute("fax", DetailFieldType.TEXTBOX);
        detailDataModel.addAttribute("address", DetailFieldType.TEXTAREA).mandatory(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getDefaultTitle(Store entity) {
        return ControlConfigUtils.getString("label.Store.detail.title") + UIConstants.BLANK + entity.getCode();
    }

}
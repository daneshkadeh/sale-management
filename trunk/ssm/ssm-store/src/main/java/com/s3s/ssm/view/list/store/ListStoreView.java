/*
 * ListStoreView
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

package com.s3s.ssm.view.list.store;

import java.util.List;

import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.store.EditStoreView;

/**
 * @author Chanhchua
 * 
 */
public class ListStoreView extends AbstractListView<Store> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("storedAddress", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("importAddress", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("exportAddress", FieldTypeEnum.TEXTBOX));
        listDataModel.add(new DetailAttribute("isEnabled", FieldTypeEnum.TEXTBOX));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Store>> getEditViewClass() {
        return EditStoreView.class;
    }

}

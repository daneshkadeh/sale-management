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
import com.s3s.ssm.view.detail.store.EditStoreView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

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
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("address", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("storedAddress", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("importAddress", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("exportAddress", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("isEnabled", DetailFieldType.TEXTBOX));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Store>> getEditViewClass() {
        return EditStoreView.class;
    }

}

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

import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.view.detail.store.EditStoreView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListStoreView extends AbstractListView<Store> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("address", ListRendererType.TEXT);
        listDataModel.addColumn("storedAddress", ListRendererType.TEXT);
        listDataModel.addColumn("importAddress", ListRendererType.TEXT);
        listDataModel.addColumn("exportAddress", ListRendererType.TEXT);
        listDataModel.addColumn("manager", ListRendererType.TEXT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Store>> getEditViewClass() {
        return EditStoreView.class;
    }

}
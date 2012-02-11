/*
 * EditCheckStoreView
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

import java.util.List;

import com.s3s.ssm.entity.store.CheckStore;
import com.s3s.ssm.entity.store.DetailCheckStore;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditCheckStoreView extends AbstractMasterDetailView<CheckStore, DetailCheckStore> {

    public EditCheckStoreView(CheckStore entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractEditView<DetailCheckStore>> getChildDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getChildFieldName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void saveOrUpdate(CheckStore masterEntity, List<DetailCheckStore> detailEntities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, CheckStore entity) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void addDetailIntoMaster(CheckStore masterEntity, DetailCheckStore detailEntity) {
        // TODO Auto-generated method stub

    }

}

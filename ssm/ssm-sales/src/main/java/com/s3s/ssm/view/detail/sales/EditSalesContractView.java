/*
 * EditSalesContractView
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
package com.s3s.ssm.view.detail.sales;

import java.util.List;
import java.util.Map;

import com.s3s.ssm.entity.sales.DetailSalesContract;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.AbstractMasterDetailView;
import com.s3s.ssm.view.edit.DetailDataModel;
import com.s3s.ssm.view.list.ListDataModel;

public class EditSalesContractView extends AbstractMasterDetailView<SalesContract, DetailSalesContract> {

    public EditSalesContractView(Map<String, Object> entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initialListDetailPresentationView(ListDataModel listDataModel) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractEditView<DetailSalesContract>> getChildDetailViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getChildFieldName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void saveOrUpdate(SalesContract masterEntity, List<DetailSalesContract> detailEntities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialPresentationView(DetailDataModel detailDataModel, SalesContract entity) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void addDetailIntoMaster(SalesContract masterEntity, DetailSalesContract detailEntity) {
        // TODO Auto-generated method stub

    }

}

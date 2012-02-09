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

import com.s3s.ssm.entity.sales.DetailSalesContract;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel;
import com.s3s.ssm.view.AbstractDetailView;
import com.s3s.ssm.view.AbstractMasterDetailView;

public class EditSalesContractView extends AbstractMasterDetailView<SalesContract, DetailSalesContract> {

    public EditSalesContractView(SalesContract entity) {
        super(entity);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initialListDetailPresentationView(List<DetailAttribute> listDataModel) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractDetailView<DetailSalesContract>> getChildDetailViewClass() {
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
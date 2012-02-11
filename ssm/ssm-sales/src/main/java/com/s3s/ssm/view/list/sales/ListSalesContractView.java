/*
 * ListSalesContractView
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
package com.s3s.ssm.view.list.sales;

import java.util.List;

import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;

public class ListSalesContractView extends AbstractListView<SalesContract> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        // TODO Auto-generated method stub

    }

    @Override
    protected Class<? extends AbstractEditView<SalesContract>> getEditViewClass() {
        // TODO Auto-generated method stub
        return null;
    }

}

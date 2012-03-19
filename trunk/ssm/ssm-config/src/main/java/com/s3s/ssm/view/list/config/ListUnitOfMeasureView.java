/*
 * ListUnitOfMeasureView
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
package com.s3s.ssm.view.list.config;

import java.util.List;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.config.EditUnitOfMeasureView;

public class ListUnitOfMeasureView extends AbstractListView<UnitOfMeasure> {

    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("uomCategory", DetailFieldType.DROPDOWN));
        listDataModel.add(new DetailAttribute("isBaseMeasure", DetailFieldType.DROPDOWN));
    }

    @Override
    protected Class<? extends AbstractEditView<UnitOfMeasure>> getEditViewClass() {
        return EditUnitOfMeasureView.class;
    }

    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.UOM;
    }
}

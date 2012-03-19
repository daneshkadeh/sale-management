/*
 * ListOperatorView
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

package com.s3s.ssm.view.list.operator;

import java.util.List;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.model.DetailAttribute;
import com.s3s.ssm.model.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.AbstractEditView;
import com.s3s.ssm.view.AbstractListView;
import com.s3s.ssm.view.detail.operator.EditOperatorView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListOperatorView extends AbstractListView<Operator> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("username", DetailFieldType.TEXTBOX));
        // listDataModel.add(new DetailAttribute("fullname", FieldTypeEnum.TEXTBOX));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Operator>> getEditViewClass() {
        return EditOperatorView.class;
    }

}

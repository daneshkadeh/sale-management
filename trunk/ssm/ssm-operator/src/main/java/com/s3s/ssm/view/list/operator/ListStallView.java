/*
 * ListStallView
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

import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.view.detail.operator.EditStallView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.edit.DetailAttribute;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;
import com.s3s.ssm.view.list.AbstractListView;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ListStallView extends AbstractListView<Stall> {

    /**
     * 
     */
    private static final long serialVersionUID = -4623351888966805171L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(List<DetailAttribute> listDataModel, List<String> summaryFieldNames) {
        listDataModel.add(new DetailAttribute("code", DetailFieldType.TEXTBOX));
        listDataModel.add(new DetailAttribute("name", DetailFieldType.TEXTBOX));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditView<Stall>> getEditViewClass() {
        return EditStallView.class;
    }

}

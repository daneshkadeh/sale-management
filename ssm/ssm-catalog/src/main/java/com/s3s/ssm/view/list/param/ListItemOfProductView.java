/*
 * ListItemView
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
package com.s3s.ssm.view.list.param;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.view.detail.param.EditItemView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

public class ListItemOfProductView extends AbstractListView<Item> {
    private static final long serialVersionUID = -9117809072273053963L;

    public ListItemOfProductView(Map<String, Object> request) {
        super(request);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("product", ListColumnType.TEXT);
        listDataModel.addColumn("sumUomName", ListColumnType.TEXT);
        listDataModel.addColumn("baseSellPrice", ListColumnType.TEXT);
        listDataModel.addColumn("currency", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Item>> getEditViewClass() {
        return EditItemView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.add(Restrictions.eq("product", getDaoHelper().getDao(getParentClass()).findById(getParentId())));
        return dc;
    }
}

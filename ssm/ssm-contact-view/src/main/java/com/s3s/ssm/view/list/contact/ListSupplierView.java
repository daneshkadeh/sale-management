/*
 * ListSupplierView
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
package com.s3s.ssm.view.list.contact;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.view.detail.contact.EditMultiSupplierView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * 
 * @author phamcongbang
 * 
 */
public class ListSupplierView extends AbstractListView<Partner> {
    private static final long serialVersionUID = -1414670444682843015L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.LINK); // TODO Phuc: testing
        listDataModel.addColumn("name", ListRendererType.TEXT);
        listDataModel.addColumn("phone", ListRendererType.TEXT);
        listDataModel.addColumn("fax", ListRendererType.TEXT);
        listDataModel.addColumn("email", ListRendererType.TEXT);
        listDataModel.addColumn("website", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Partner>> getEditViewClass() {
        return EditMultiSupplierView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.createCriteria("listProfiles").add(Restrictions.eq("type", PartnerProfileTypeEnum.SUPPLIER));
        return dc;
    }
}

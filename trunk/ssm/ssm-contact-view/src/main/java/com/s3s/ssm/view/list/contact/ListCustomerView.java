/*
 * ListCustomerView
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
import com.s3s.ssm.view.detail.contact.EditMultiCustomerView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AbstractListView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

/**
 * 
 * @author phamcongbang
 * 
 */
public class ListCustomerView extends AbstractListView<Partner> {
    /**
     * 
     */
    private static final long serialVersionUID = 2964366183405416076L;

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListColumnType.TEXT);
        listDataModel.addColumn("name", ListColumnType.TEXT);
        listDataModel.addColumn("phone", ListColumnType.TEXT);
        listDataModel.addColumn("fax", ListColumnType.TEXT);
        listDataModel.addColumn("email", ListColumnType.TEXT);
        listDataModel.addColumn("website", ListColumnType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Partner>> getEditViewClass() {
        return EditMultiCustomerView.class;
    }

    @Override
    protected DetachedCriteria getCriteriaForView() {
        DetachedCriteria dc = super.getCriteriaForView();
        dc.createCriteria("listProfiles").add(Restrictions.eq("type", PartnerProfileTypeEnum.CUSTOMER));
        return dc;
    }

}

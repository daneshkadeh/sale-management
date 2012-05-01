/*
 * ListBasicInformationView
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

import javax.swing.Icon;

import com.s3s.ssm.entity.config.Institution;
import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.view.detail.config.EditInstitutionView;
import com.s3s.ssm.view.edit.AbstractEditView;
import com.s3s.ssm.view.list.AListEntityView;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

public class ListInstitutionView extends AListEntityView<Institution> {
    private static final long serialVersionUID = 4631796600743673793L;

    public ListInstitutionView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("code", ListRendererType.TEXT);
        listDataModel.addColumn("companyName", ListRendererType.TEXT);
        listDataModel.addColumn("agent", ListRendererType.TEXT);
        listDataModel.addColumn("tel", ListRendererType.TEXT);
        listDataModel.addColumn("website", ListRendererType.TEXT);
        listDataModel.addColumn("companyAddress", ListRendererType.TEXT);
    }

    @Override
    protected Class<? extends AbstractEditView<Institution>> getEditViewClass() {
        return EditInstitutionView.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ACLResourceEnum registerACLResource() {
        return ACLResourceEnum.BASIC_INFORMATION;
    }

}

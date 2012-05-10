/*
 * ListTestDTOComponent
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

import javax.swing.Icon;

import com.s3s.ssm.dto.finance.TestDTO;
import com.s3s.ssm.view.list.AListComponent;
import com.s3s.ssm.view.list.ListDataModel;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Phan Hong Phuc
 * @since May 10, 2012
 */
public class ListTestDTOComponent extends AListComponent<TestDTO> {
    private static final long serialVersionUID = -7786016911707699888L;

    public ListTestDTOComponent(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialPresentationView(ListDataModel listDataModel) {
        listDataModel.addColumn("attribute1", ListRendererType.TEXT);
        listDataModel.addColumn("attribute2", ListRendererType.TEXT);
        listDataModel.addColumn("attribute3", ListRendererType.TEXT);
    }
}

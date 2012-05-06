/*
 * ProductSearchComponent
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

package com.s3s.ssm.view.component;

import com.s3s.ssm.entity.sales.SalesContract;

/**
 * @author Phan Hong Phuc
 * @since Apr 24, 2012
 */
public class SalesContractSearchComponent extends ASearchComponent<SalesContract> {
    private static final long serialVersionUID = -7923869563577246501L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getDisplayAttributes() {
        return new String[] { "code" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getAttributeColumns() {
        return new String[] { "code", "supplier", "dateContract" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getSearchedOnAttributes() {
        return new String[] { "code", "supplier", "dateContract" };
    }
}

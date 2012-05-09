/*
 * PartnerSearchComponent
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

import com.s3s.ssm.entity.contact.Partner;

/**
 * @author Phan Hong Phuc
 * @since Apr 22, 2012
 */
public class PartnerSearchComponent<T extends Partner> extends ASearchComponent<Partner> {
    private static final long serialVersionUID = -8050158314408650710L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getDisplayAttributes() {
        return new String[] { "name" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getAttributeColumns() {
        return new String[] { "code", "title", "name" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getSearchedOnAttributes() {
        return new String[] { "code", "name" };
    }

}
